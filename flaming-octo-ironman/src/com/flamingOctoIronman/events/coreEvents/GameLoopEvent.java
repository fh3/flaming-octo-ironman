package com.flamingOctoIronman.events.coreEvents;


public class GameLoopEvent extends CoreEvent{
	public GameLoopEvent() {
		super();
	}

	@Override
	public String getName() {
		return GameLoopEvent.class.getSimpleName();
	}
}
