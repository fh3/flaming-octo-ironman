package com.flamingOctoIronman.core.event;


/**
 * This <code>CoreEvent</code> is published during the initialization part of the game's life.
 * @author Quint
 *
 */
public final class InitializationEvent extends CoreEvent {
	
	@Override
	public String getName() {
		return InitializationEvent.class.getSimpleName();
	}


}
