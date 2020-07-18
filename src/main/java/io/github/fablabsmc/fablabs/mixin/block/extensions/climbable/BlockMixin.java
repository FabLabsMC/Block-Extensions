package io.github.fablabsmc.fablabs.mixin.block.extensions.climbable;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ClimbableBlockEvents;
import io.github.fablabsmc.fablabs.impl.block.extensions.ClimbableBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.block.Block;

import net.fabricmc.fabric.api.event.Event;

@Mixin(Block.class)
public abstract class BlockMixin implements ClimbableBlockExtension {
	@Unique
	private Event<ClimbableBlockEvents.ClimbBlock> fabric_climbEvent = ClimbableBlockExtension.createEvent();

	public Event<ClimbableBlockEvents.ClimbBlock> fabric_getClimbEvent() {
		return this.fabric_climbEvent;
	}
}
