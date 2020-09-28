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

package io.github.fablabsmc.fablabs.mixin.block.extensions.climbable;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ClimbableBlockEvents;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.TypedTriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import net.fabricmc.fabric.api.util.TriState;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
	private LivingEntityMixin() {
		super(null, null);
	}

	@Unique
	private TypedTriState<Double> currentClimbingSpeed = TypedTriState.defaultValue(0.0D);

	@Shadow
	public abstract BlockState getBlockState();

	@Redirect(method = "isClimbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isIn(Lnet/minecraft/tag/Tag;)Z"))
	private boolean checkIfClimbing(Block block, Tag<Block> tag) {
		if (block.isIn(tag)) {
			this.currentClimbingSpeed = ClimbableBlockEvents.event(block).invoker().getClimbingSpeed(this.getBlockState(), this.world, this, this.getBlockPos());
			return this.currentClimbingSpeed.getResult() != TriState.FALSE;
		}

		return false;
	}

	// TODO: Apply changed or no velocity.
}
