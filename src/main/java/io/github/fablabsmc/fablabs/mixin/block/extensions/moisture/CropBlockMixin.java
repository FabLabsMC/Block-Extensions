package io.github.fablabsmc.fablabs.mixin.block.extensions.moisture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(CropBlock.class)
abstract class CropBlockMixin {
	@Unique
	private static final ThreadLocal<Integer> MOSTURE_LEVEL = new ThreadLocal<>();

	@Redirect(method = "getAvailableMoisture", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private static boolean allowOtherBlocks(BlockState state, Block block, Block unusedBlock, BlockView world, BlockPos pos) {
		// block is Farmland
		if (state.isOf(block)) {
			return true; // Vanilla codepath
		}

		return false; // TODO:
	}
}
