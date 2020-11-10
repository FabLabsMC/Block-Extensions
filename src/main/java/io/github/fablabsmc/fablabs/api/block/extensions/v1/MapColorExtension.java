package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface MapColorExtension {
	MaterialColor getColor(BlockState state, BlockView world, BlockPos pos);
}
