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

package io.github.fablabsmc.fablabs.mixin.block.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.BlockExtensions;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.BlockLootTableExtension;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.BlockMoistureExtension;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.EnchantmentTablePowerExtension;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.PistonBehaviorExtension;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.SlipperinessExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@Mixin(AbstractBlock.AbstractBlockState.class)
abstract class AbstractBlockStateMixin implements BlockExtensions {
	@Shadow
	public abstract BlockState asBlockState();
	@Shadow
	public abstract Block getBlock();
	@Shadow
	public abstract PistonBehavior getPistonBehavior();

	@Override
	public PistonBehavior getPistonBehavior(World world, BlockPos pos, Direction motionDirection, Direction pistonDirection) {
		if (this.getBlock() instanceof PistonBehaviorExtension) {
			return ((PistonBehaviorExtension) this.getBlock()).getPistonBehavior(this.asBlockState(), world, pos, motionDirection, pistonDirection);
		}

		return this.getPistonBehavior();
	}

	@Override
	public int getEnchantmentTablePower(World world, BlockPos pos) {
		if (this.getBlock() instanceof EnchantmentTablePowerExtension) {
			return ((EnchantmentTablePowerExtension) this.getBlock()).getEnchantmentTablePower(this.asBlockState(), world, pos);
		}

		return 0;
	}

	@Override
	public float getSlipperiness(World world, BlockPos pos, Entity entity) {
		if (this.getBlock() instanceof SlipperinessExtension) {
			return ((SlipperinessExtension) this.getBlock()).getSlipperiness(this.asBlockState(), world, pos, entity);
		}

		return this.getBlock().getSlipperiness();
	}

	@Override
	public Identifier getLootTableId(LootContext.Builder builder) {
		if (this.getBlock() instanceof BlockLootTableExtension) {
			return ((BlockLootTableExtension) this.getBlock()).getLootTableId(this.asBlockState(), builder);
		}

		return this.getBlock().getLootTableId();
	}

	@Override
	public int getMoisture(BlockView world, BlockPos pos) {
		if (this.getBlock() instanceof BlockMoistureExtension) {
			return ((BlockMoistureExtension) this.getBlock()).getMoisture(this.asBlockState(), world, pos);
		}

		return 0;
	}
}
