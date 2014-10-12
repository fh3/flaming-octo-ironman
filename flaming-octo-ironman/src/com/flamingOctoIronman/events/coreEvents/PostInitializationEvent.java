package com.flamingOctoIronman.events.coreEvents;

public final class PostInitializationEvent extends CoreEvent {

	@Override
	public String getName() {
		return PostInitializationEvent.class.getSimpleName();
	}

}
