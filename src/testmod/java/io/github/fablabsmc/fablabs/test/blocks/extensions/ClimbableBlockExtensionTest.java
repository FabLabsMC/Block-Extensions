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

package io.github.fablabsmc.fablabs.test.blocks.extensions;

import java.util.HashMap;
import java.util.Map;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ClimbableBlockEvents;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.TypedTriState;
import org.spongepowered.asm.mixin.MixinEnvironment;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ClimbableBlockExtensionTest implements ModInitializer {
	protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);

	private static final Map<Direction, VoxelShape> SHAPES = Util.make(new DefaultReturnHashMap<>(BOTTOM_SHAPE), map -> {
		map.put(Direction.DOWN, TOP_SHAPE);
		map.put(Direction.UP, BOTTOM_SHAPE);
		map.put(Direction.WEST, WEST_SHAPE);
		map.put(Direction.EAST, EAST_SHAPE);
		map.put(Direction.NORTH, NORTH_SHAPE);
		map.put(Direction.SOUTH, SOUTH_SHAPE);
	});

	public static final Block TEST_CLIMBABLE_BLOCK = ExtensionUtils.registerWithItem("test_climbable_block", new TestBlock());

	@Override
	public void onInitialize() {
		MixinEnvironment.getCurrentEnvironment().audit();

		ClimbableBlockEvents.event(ClimbableBlockExtensionTest.TEST_CLIMBABLE_BLOCK).register((state, world, entity, pos) -> {
			if (state.get(TestBlock.FACING) == Direction.DOWN || state.get(TestBlock.FACING) == Direction.UP) {
				return TypedTriState.falseValue(0.0D);
			}

			return TypedTriState.trueValue(1000.35D);
		});
	}

	private static class TestBlock extends Block {
		private static final DirectionProperty FACING = Properties.FACING;

		TestBlock() {
			super(FabricBlockSettings.of(Material.WOOD));
			this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
		}

		@Override
		public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return SHAPES.get(state.get(FACING));
		}

		@Override
		public BlockState getPlacementState(ItemPlacementContext context) {
			return this.getDefaultState().with(FACING, context.getSide());
		}

		@Override
		protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
			builder.add(FACING);
		}
	}

	static class DefaultReturnHashMap<K, V> extends HashMap<K, V> {
		private final V defaultValue;

		public DefaultReturnHashMap(V defaultValue) {
			this.defaultValue = defaultValue;
		}

		@Override
		public V get(Object key) {
			V value = super.get(key);
			return value != null ? value : this.getDefaultValue();
		}

		private V getDefaultValue() {
			return this.defaultValue;
		}
	}
}
