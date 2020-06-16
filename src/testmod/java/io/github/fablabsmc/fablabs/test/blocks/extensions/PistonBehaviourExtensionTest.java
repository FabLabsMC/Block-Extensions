package io.github.fablabsmc.fablabs.test.blocks.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.PistonBehaviourExtension;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class PistonBehaviourExtensionTest implements ModInitializer {
	public static final Block TEST_BLOCK = ExtensionUtils.registerWithItem("test_piston_behaviour_block", new TestBlock());

	@Override
	public void onInitialize() {

	}

	// Do not push or retract if the block is in the nether
	static class TestBlock extends Block implements PistonBehaviourExtension {
		TestBlock() {
			super(FabricBlockSettings.of(Material.METAL));
		}

		@Override
		public PistonBehavior getPistonBehavior(BlockState state, World world, BlockPos pos, Direction motionDirection, Direction pistonDirection) {
			if (world.getRegistryKey() == World.NETHER) {
				return PistonBehavior.BLOCK;
			}

			return PistonBehavior.NORMAL;
		}
	}
}
