package io.github.fablabsmc.fablabs.mixin.block.extensions.mapcolor;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.MapColorExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(AbstractBlock.AbstractBlockState.class)
abstract class AbstractBlockStateMixin {
	@Shadow public abstract Block getBlock();

	@Shadow protected abstract BlockState asBlockState();

	@Inject(method = "getTopMaterialColor", at = @At("HEAD"), cancellable = true)
	private void extraContextMapColor(BlockView world, BlockPos pos, CallbackInfoReturnable<MaterialColor> cir) {
		if (this.getBlock() instanceof MapColorExtension) {
			cir.setReturnValue(((MapColorExtension) this.getBlock()).getColor(this.asBlockState(), world, pos));
		}
	}
}
