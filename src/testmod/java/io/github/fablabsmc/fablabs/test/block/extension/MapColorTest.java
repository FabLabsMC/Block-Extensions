/*
 * Copyright 2020 FabLabsMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.fablabsmc.fablabs.test.block.extension;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.MapColorExtension;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.util.NbtType;

public final class MapColorTest implements ModInitializer {
	private static final Block MAP_COLOR_TEST = ExtensionUtils.registerWithItem("map_color_test", new TestBlock());

	@Override
	public void onInitialize() {
	}

	private static final class TestBlock extends Block implements MapColorExtension {
		private static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);

		TestBlock() {
			// The Map Color specified by the Material is going to be ignored in this context anyways
			super(FabricBlockSettings.of(Material.METAL));
			this.setDefaultState(this.getStateManager().getDefaultState().with(COLOR, DyeColor.WHITE));
		}

		@Override
		public MaterialColor getColor(BlockView world, BlockPos pos, BlockState state) {
			return state.get(COLOR).getMaterialColor();
		}

		@Override
		public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> list) {
			// Add an entry for each colorO
			for (DyeColor color : DyeColor.values()) {
				final ItemStack stack = new ItemStack(this);
				stack.getOrCreateTag().putString("Color", color.name());

				list.add(stack);
			}
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (world.isClient()) {
				return super.onUse(state, world, pos, player, hand, hit);
			}

			final ItemStack stack = player.getStackInHand(hand);

			if (stack.getItem() instanceof DyeItem) {
				world.setBlockState(pos, state.with(COLOR, ((DyeItem) stack.getItem()).getColor()));

				if (!player.isCreative()) {
					stack.decrement(1);
				}

				return ActionResult.SUCCESS;
			}

			return super.onUse(state, world, pos, player, hand, hit);
		}

		@Override
		public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
			if (world.isClient()) {
				return;
			}

			if (itemStack.hasTag()) {
				//noinspection ConstantConditions
				if (itemStack.getTag().contains("Color", NbtType.STRING)) {
					// Set color of block based on item's nbt
					try {
						final DyeColor color = DyeColor.valueOf(itemStack.getTag().getString("Color"));
						world.setBlockState(pos, state.with(COLOR, color));
					} catch (IllegalArgumentException ignored) {
						// Ignore invalid nbt
					}
				}
			}
		}

		@Override
		public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
			// Copy state's color and put it onto item stack
			stack.getOrCreateTag().putString("Color", state.get(COLOR).name());
		}

		@Override
		protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
			builder.add(COLOR);
		}
	}
}
