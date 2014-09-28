package com.flamingOctoIronman.events.coreEvents;


public final class StartUpEvent extends CoreEvent{

	public StartUpEvent() {
		super();
	}

	@Override
	public String getName() {
		return StartUpEvent.class.getSimpleName();
	}
}
