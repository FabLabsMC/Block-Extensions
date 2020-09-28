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

import io.github.fablabsmc.fablabs.impl.block.extensions.ClimbableBlockExtension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.event.Event;

/**
 * Holds the event for climbable blocks.
 * Note the block must be in the {@link BlockTags#CLIMBABLE climbable tag} for these events to be fired.
 */
public final class ClimbableBlockEvents {
	public static Event<Climb> event(Block block) {
		return ((ClimbableBlockExtension) block).fabric_getClimbEvent();
	}

	public interface Climb {
		/**
		 * Gets the climbing speed of a block.
		 *
		 * @param state the block state
		 * @param world the world
		 * @param entity the entity that is climbing
		 * @param pos the position the entity is climbing at
		 * @return an action result which contains the climbing speed.
		 * If the result is {@link net.minecraft.util.ActionResult#FAIL}, the entity will not climb.
		 */
		TypedTriState<Double> getClimbingSpeed(BlockState state, World world, Entity entity, BlockPos pos);
	}
}
