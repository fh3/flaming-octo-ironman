package com.flamingOctoIronman.core.event;

public final class PostInitializationEvent extends CoreEvent {

	@Override
	public String getName() {
		return PostInitializationEvent.class.getSimpleName();
	}

}
