package io.github.fablabsmc.fablabs.mixin.block.extensions.piston;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.AbstractBlockStateExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin {
	/**
	 * Forward the default call to extension method.
	 * Will call vanilla if the block behind the block state does not implement the extension.
	 */
	@Redirect(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;"))
	private static PistonBehavior onPistonBehaviour(BlockState state, BlockState duplicateState, World world, BlockPos pos, Direction motionDir, boolean canBreak, Direction pistonDir) {
		return AbstractBlockStateExtensions.getExtensions(state).getPistonBehavior(world, pos, motionDir, pistonDir);
	}
}
