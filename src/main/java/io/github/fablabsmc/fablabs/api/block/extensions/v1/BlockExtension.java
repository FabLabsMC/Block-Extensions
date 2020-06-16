package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.minecraft.block.Block;

public interface BlockExtension {
	default Block getBlock() {
		return (Block) this;
	}
}
