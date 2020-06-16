package io.github.fablabsmc.fablabs.mixin.block.extensions.piston;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.AbstractBlockStateExtensions;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.PistonBehaviourExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(PistonHandler.class)
public abstract class PistonHandlerMixin {
	@Shadow
	@Final
	private World world;
	@Shadow
	@Final
	private Direction motionDirection;
	@Shadow
	@Final
	private Direction pistonDirection;

	@Unique
	private BlockPos fabric_currentPos;

	@Inject(method = "tryMove", at = @At(value = "TAIL", target = "Lnet/minecraft/block/BlockState;getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void getPosBeforeBreak(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir, BlockState state, Block block, int i, int j, int l, BlockPos currentPos) {
		this.fabric_currentPos = currentPos;
	}

	/**
	 * Forward the default call to extension method.
	 * Will call vanilla if the block behind the block state does not implement the extension.
	 */
	@Redirect(method = "tryMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;"))
	private PistonBehavior getBehaviourBeforeBreakingBlocks(BlockState state, BlockPos pos, Direction dir) {
		final AbstractBlockStateExtensions extensions = AbstractBlockStateExtensions.getExtensions(state);

		if (extensions.asBlockState().getBlock() instanceof PistonBehaviourExtension) {
			return extensions.getPistonBehavior(this.world, this.fabric_currentPos, this.motionDirection, this.pistonDirection);
		}

		return state.getPistonBehavior();
	}
}
