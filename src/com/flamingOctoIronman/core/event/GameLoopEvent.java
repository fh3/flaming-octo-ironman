package com.flamingOctoIronman.core.event;

import com.flamingOctoIronman.debugging.Verbosity;


public class GameLoopEvent extends CoreEvent{
	public GameLoopEvent() {
		super();
	}

	@Override
	public String getName() {
		return GameLoopEvent.class.getSimpleName();
	}
	
	@Override
	public Verbosity getVerbosity(){
		return Verbosity.HIGH;
	}
}
