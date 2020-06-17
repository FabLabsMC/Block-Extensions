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

package io.github.fablabsmc.fablabs.mixin.block.extensions.slipperiness;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.AbstractBlockStateExtensions;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.SlipperinessExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
	@Unique
	private BlockPos fabric_slipperinessTestBlock;

	private BoatEntityMixin() {
		super(null, null);
	}

	@Inject(method = "method_7548", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void captureBlockPos(CallbackInfoReturnable<Float> cir, Box box, Box box2, int i, int j, int k, int l, int m, int n, VoxelShape voxelShape, float f, int o, BlockPos.Mutable mutable) {
		this.fabric_slipperinessTestBlock = mutable;
	}

	@Redirect(method = "method_7548", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
	private float modifySlipperiness(Block block) {
		final BlockState state = this.world.getBlockState(this.fabric_slipperinessTestBlock);
		return AbstractBlockStateExtensions.getExtensions(state).getSlipperiness(this.world, this.fabric_slipperinessTestBlock, this);
	}
}
