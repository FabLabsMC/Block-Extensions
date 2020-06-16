package io.github.fablabsmc.fablabs.mixin.block.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.AbstractBlockStateExtensions;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.PistonBehaviourExtension;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

@Mixin(AbstractBlock.AbstractBlockState.class)
@Implements(@Interface(iface = AbstractBlockStateExtensions.class, prefix = "extensions$"))
public abstract class AbstractBlockStateMixin implements AbstractBlockStateExtensions {
	// Must be "shadow$" prefixed to avoid overwrites
	@Shadow public abstract BlockState shadow$asBlockState();
	@Shadow public abstract Block getBlock();
	@Shadow public abstract PistonBehavior getPistonBehavior();

	@Intrinsic
	public BlockState extensions$asBlockState() {
		return this.shadow$asBlockState();
	}

	@Override
	public PistonBehavior getPistonBehavior(World world, BlockPos pos, Direction motionDirection, Direction pistonDirection) {
		if (this.getBlock() instanceof PistonBehaviourExtension) {
			return ((PistonBehaviourExtension) this.getBlock()).getPistonBehavior(this.shadow$asBlockState(), world, pos, motionDirection, pistonDirection);
		}

		return this.getPistonBehavior();
	}
}
