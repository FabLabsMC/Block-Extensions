package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface BlockMoistureExtension {
	int getMoisture(BlockState state, BlockView world, BlockPos pos);
}
