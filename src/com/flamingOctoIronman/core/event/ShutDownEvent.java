package com.flamingOctoIronman.core.event;


public class ShutDownEvent extends CoreEvent{
	public ShutDownEvent() {
		super();
	}

	@Override
	public String getName() {
		return ShutDownEvent.class.getSimpleName();
	}
}
