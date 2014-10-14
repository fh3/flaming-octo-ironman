package com.flamingOctoIronman.core.event;

/**
 * This {@link CoreEvent} is published during the shutdown part of the game's life.
 * @author Quint Heinecke
 *
 */
public class ShutDownEvent extends CoreEvent{
	/**
	 * Nothing to see here, move along.
	 */
	public ShutDownEvent() {
		super();
	}

	/**
	 * @return "ShutDownEvent"
	 */
	@Override
	public String getName() {
		return ShutDownEvent.class.getSimpleName();
	}
}
