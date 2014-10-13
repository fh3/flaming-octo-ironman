package com.flamingOctoIronman.core.event;


public final class StartUpEvent extends CoreEvent{

	public StartUpEvent() {
		super();
	}

	@Override
	public String getName() {
		return StartUpEvent.class.getSimpleName();
	}
}
