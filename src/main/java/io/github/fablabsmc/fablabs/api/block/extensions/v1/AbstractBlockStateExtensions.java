package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface AbstractBlockStateExtensions {
	static AbstractBlockStateExtensions getExtensions(AbstractBlock.AbstractBlockState state) {
		return (AbstractBlockStateExtensions) state;
	}

	BlockState asBlockState();

	PistonBehavior getPistonBehavior(World world, BlockPos pos, Direction motionDirection, Direction pistonDirection);
}
