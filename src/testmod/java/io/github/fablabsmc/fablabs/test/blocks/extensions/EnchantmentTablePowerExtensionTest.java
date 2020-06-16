/*
 * Copyright 2020 FabLabsMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.fablabsmc.fablabs.test.blocks.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.EnchantmentTablePowerExtension;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantmentTablePowerExtensionTest implements ModInitializer {
	public static final Block TEST_POWER_BLOCK = ExtensionUtils.registerWithItem("test_enchantment_power_block", new TestBlock());

	@Override
	public void onInitialize() {
	}

	private static class TestBlock extends Block implements EnchantmentTablePowerExtension {
		TestBlock() {
			super(FabricBlockSettings.of(Material.WOOD));
		}

		@Override
		public int getEnchantmentTablePower(BlockState state, World world, BlockPos pos) {
			if (world.getRegistryKey().equals(World.NETHER)) {
				return 20;
			}

			return 1;
		}
	}
}
