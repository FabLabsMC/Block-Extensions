package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface MapColorExtension {
	@ApiStatus.OverrideOnly
	MaterialColor getColor(BlockState state, BlockView world, BlockPos pos);
}
