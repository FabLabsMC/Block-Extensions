package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.block.BlockState;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;

public interface BlockLootTableExtension {
	@ApiStatus.OverrideOnly
	Identifier getLootTableId(BlockState state, LootContext.Builder builder);
}
