package com.flamingOctoIronman.events;

/**
 * This <code>CoreEvent</code> is published during the initialization part of the game's life.
 * @author Quint
 *
 */
public final class InitializationEvent extends CoreEvent {
	
	public InitializationEvent(){
		super();
	}

	@Override
	public boolean compareClass(String s) {
		if(s.equals(InitializationEvent.class.getSimpleName())){
			return true;
		} else{
			return false;
		}
	}


}
