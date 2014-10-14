package com.flamingOctoIronman.core.event;


/**
 * This {@link CoreEvent} is published during the initialization part of the game's life.
 * @author Quint Heinecke
 *
 */
public final class InitializationEvent extends CoreEvent {
	/**
	 * Nothing to see here, move along.
	 */
	public InitializationEvent(){
		super();
	}
	
	/**
	 * @return "InitializationEvent"
	 */
	@Override
	public String getName() {
		return InitializationEvent.class.getSimpleName();
	}


}
