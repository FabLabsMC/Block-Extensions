package io.github.fablabsmc.fablabs.test.blocks.extensions;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ExtensionUtils {
	;
	public static final ItemGroup TEST_GROUP = FabricItemGroupBuilder.build(id("tests"), () -> new ItemStack(Items.PAPER));

	public static Identifier id(String path) {
		return new Identifier("fablabs_block_extension_test");
	}

	public static Block register(String path, Block block) {
		return Registry.register(Registry.BLOCK, id(path), block);
	}

	public static Block registerWithItem(String path, Block block) {
		return registerWithItem(path, block, new Item.Settings());
	}

	public static Block registerWithItem(String path, Block block, Item.Settings settings) {
		Block b = register(path, block);
		Registry.register(Registry.ITEM, id(path), new BlockItem(block, settings.group(TEST_GROUP)));
		return b;
	}
}
