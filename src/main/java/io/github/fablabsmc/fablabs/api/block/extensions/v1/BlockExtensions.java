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

package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import io.github.fablabsmc.fablabs.impl.block.extensions.BlockStateExtensions;

import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * The purpose of this API is to provide mods with additional context when calculating values such as how slippery a block is.
 * This class acts as a central dispatch for all block extension methods provided by Fabric API.
 * Vanilla Minecraft does support most of the methods in this class but does so with little to no context including the
 * world, position of the block or the block state.
 *
 * <p>This class is primarily for use by mods.
 * For example a mod which implements custom pistons may simply use {@link AbstractBlockState#getPistonBehavior()} in
 * order to get a blocks piston behavior.
 * However that method provides little to no context to any mods which may for example want a block which may only be
 * pushed in a certain dimension.
 * Depending on how the mod developer of the block being pushed has decided, the block may be considered as immovable
 * since the mod developer cannot guarantee the block is in a certain dimension or vice versa where the block's movement
 * exceeds what should occur.
 * To solve this problem, the custom piston may use {@link BlockExtensions#getPistonBehavior(BlockState, World, BlockPos, Direction, Direction)}
 * in order to provide the additional context a mod may need in order to determine the piston behavior.
 *
 * <p>For mods which call methods in this class, it is <em>highly</em> recommended to prefer the method with the most specific calling context.
 * For example if you have a world, block position and block state you are advised to use the corresponding method call from
 * this class in order to get the most preferred outcome of a specific block behavior.
 * There is no need for a mod to verify that a block supports a specific extension interface as the internal dispatch logic
 * will automatically select the most precise method context supported by the block.
 * Hence it is encouraged to prefer method calls from this class over method calls in {@link BlockState}.
 */
public final class BlockExtensions {
	/**
	 * Gets the piston behavior of a block, taking the world, block's state, position, motion direction and a piston's
	 * facing direction into account.
	 *
	 * @param state the block state
	 * @param world the world
	 * @param pos the position of the block
	 * @param motionDirection the direction this block will be moved in
	 * @param pistonDirection the direction the piston is facing that is causing the movement
	 * @return the piston behavior.
	 */
	public static PistonBehavior getPistonBehavior(BlockState state, World world, BlockPos pos, Direction motionDirection, Direction pistonDirection) {
		return getExtensions(state).getPistonBehavior(world, pos, motionDirection, pistonDirection);
	}

	public static int getEnchantmentTablePower(BlockState state, World world, BlockPos pos) {
		return getExtensions(state).getEnchantmentTablePower(world, pos);
	}

	public static float getSlipperiness(BlockState state, World world, BlockPos pos, Entity entity) {
		return getExtensions(state).getSlipperiness(world, pos, entity);
	}

	public static Identifier getLootTableId(BlockState state, LootContext.Builder builder) {
		return getExtensions(state).getLootTableId(builder);
	}

	private static BlockStateExtensions getExtensions(BlockState state) {
		return (BlockStateExtensions) state;
	}

	private BlockExtensions() {
	}
}
