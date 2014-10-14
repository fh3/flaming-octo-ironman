package com.flamingOctoIronman.core.event;

/**
 * This {@link CoreEvent} is published during the startup part of the game's life.
 * @author Quint Heinecke
 *
 */
public final class StartUpEvent extends CoreEvent{

	/**
	 * Nothing to see here, move along.
	 */
	public StartUpEvent() {
		super();
	}

	/**
	 * @return "StartUpEvent"
	 */
	@Override
	public String getName() {
		return StartUpEvent.class.getSimpleName();
	}
}
