package io.github.fablabsmc.fablabs.impl.block.extensions;

import io.github.fablabsmc.fablabs.api.block.extensions.v1.ClimbableBlockEvents;
import io.github.fablabsmc.fablabs.api.block.extensions.v1.TypedTriState;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ClimbableBlockExtension {
	Event<ClimbableBlockEvents.Climb> fabric_getClimbEvent();

	static Event<ClimbableBlockEvents.Climb> createEvent() {
		return EventFactory.createArrayBacked(ClimbableBlockEvents.Climb.class, callbacks -> (state, world, entity, pos) -> {
			for (ClimbableBlockEvents.Climb callback : callbacks) {
				final TypedTriState<Double> climbingSpeed = callback.getClimbingSpeed(state, world, entity, pos);

				switch (climbingSpeed.getResult()) {
				case TRUE:
				case FALSE:
					return climbingSpeed;
				default:
				}
			}

			// No modification
			return TypedTriState.defaultValue(0.0D);
		});
	}
}
