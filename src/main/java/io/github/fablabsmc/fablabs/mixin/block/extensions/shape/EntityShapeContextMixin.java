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

package io.github.fablabsmc.fablabs.mixin.block.extensions.shape;

import java.util.Optional;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ShapeContextExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Mixin(EntityShapeContext.class)
abstract class EntityShapeContextMixin implements ShapeContextExtensions {
	private ItemStack fabric_heldItem = ItemStack.EMPTY;
	private Entity fabric_entity = null;

	@Inject(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
	private void storeExtraData(Entity entity, CallbackInfo ci) {
		this.fabric_entity = entity;

		if (entity instanceof LivingEntity) {
			this.fabric_heldItem = ((LivingEntity) entity).getMainHandStack().copy(); // Make a copy
		}
	}

	@Override
	public ItemStack getHeldItem() {
		return this.fabric_heldItem;
	}

	@Override
	public Optional<Entity> getEntity() {
		return Optional.ofNullable(this.fabric_entity);
	}
}
