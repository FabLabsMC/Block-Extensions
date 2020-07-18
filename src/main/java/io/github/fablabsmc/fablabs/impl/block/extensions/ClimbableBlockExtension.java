package io.github.fablabsmc.fablabs.impl.block.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ClimbableBlockEvents;

import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ClimbableBlockExtension {
	Event<ClimbableBlockEvents.ClimbBlock> fabric_getClimbEvent();

	static Event<ClimbableBlockEvents.ClimbBlock> createEvent() {
		return EventFactory.createArrayBacked(ClimbableBlockEvents.ClimbBlock.class, callbacks -> (state, world, entity, pos) -> {
			for (ClimbableBlockEvents.ClimbBlock callback : callbacks) {
				final TypedActionResult<Double> climbingSpeed = callback.getClimbingSpeed(state, world, entity, pos);

				if (climbingSpeed.getResult() == ActionResult.FAIL) {
					return climbingSpeed;
				}

				if (climbingSpeed.getResult() == ActionResult.SUCCESS) {
					return climbingSpeed;
				}
			}

			// No modification
			return TypedActionResult.pass(0.0D);
		});
	}
}
