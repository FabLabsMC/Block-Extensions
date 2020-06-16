package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public interface PistonBehaviourExtension extends BlockExtension {
	/**
	 * Gets the piston behaviour of this block.
	 *
	 * <p>This method should be overwritten but not called.
	 *
	 * @param state the block state of this block
	 * @param world the world this is being tested in
	 * @param pos the position this block is at
	 * @param motionDirection the direction the block could move
	 * @param pistonDirection the direction the piston is facing
	 * @return the piston behaviour.
	 */
	@Deprecated
	default PistonBehavior getPistonBehavior(BlockState state, World world, BlockPos pos, Direction motionDirection, Direction pistonDirection) {
		return this.getBlock().getPistonBehavior(state);
	}
}
