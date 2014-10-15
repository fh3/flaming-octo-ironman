package com.flamingOctoIronman.core.event;

import com.flamingOctoIronman.subsystem.debugging.Verbosity;

/**
 * This {@link CoreEvent} is published whenever there is a tick.
 * @author Quint Heinecke
 *
 */
public class GameLoopEvent extends CoreEvent{
	/**
	 * Nothing to see here, move along.
	 */
	public GameLoopEvent() {
		super();
	}

	/**
	 * @return "GameLoopEvent"
	 */
	@Override
	public String getName() {
		return GameLoopEvent.class.getSimpleName();
	}
	
	/**
	 * @return {@link Verbosity#HIGH}.
	 */
	@Override
	public Verbosity getVerbosity(){
		return Verbosity.HIGH;
	}
}
