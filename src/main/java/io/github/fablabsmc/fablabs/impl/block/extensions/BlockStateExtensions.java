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

package io.github.fablabsmc.fablabs.impl.block.extensions;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * An accessor for getting values used in block extensions.
 *
 * @apiNote This interface is not for implementation by users.
 */
public interface BlockStateExtensions {
	/*
	 * Not covered:
	 * Beacon beam color - Horrid mixin in BeaconBlockEntity, this will require better tooling in future to support.
	 */

	/**
	 * Gets block extensions for a block state.
	 *
	 * @param state the block state
	 * @return the block extensions
	 */
	static BlockStateExtensions get(AbstractBlock.AbstractBlockState state) {
		return (BlockStateExtensions) state;
	}

	PistonBehavior getPistonBehavior(World world, BlockPos pos, Direction motionDirection, Direction pistonDirection);

	/**
	 * Gets the enchantment power this block provides.
	 * If this block should provide no power, the return value should be 0.
	 *
	 * <p>Note that the {@link Blocks#BOOKSHELF bookshelves} will return 0.
	 *
	 * @param world the world
	 * @param pos the position of the block
	 * @return the enchantment power.
	 */
	int getEnchantmentTablePower(World world, BlockPos pos);

	float getSlipperiness(World world, BlockPos pos, Entity entity);

	Identifier getLootTableId(LootContext.Builder builder);

	// -- BLOCK

	// TODO: getVelocityMultipler - State, World, pos, entity

	// TODO: getJumpVelocityMultiplier - State, World, pos, entity

	// --- hypothetical features

	// TODO: Sticky blocks (can pistons use it like a slime/honey block)

	// TODO: Plants

	// TODO: Exp drop?

	// TODO: Connectable to fences/walls?
}
