package com.flamingOctoIronman.core.event;

/**
 * This {@link CoreEvent} is published during the post-initialization part of the game's life.
 * @author Quint Heinecke
 *
 */
public final class PostInitializationEvent extends CoreEvent {
	/**
	 * Nothing to see here, move along.
	 */
	public PostInitializationEvent(){
		super();
	}
	
	/**
	 * @return "PostInitializationEvent"
	 */
	@Override
	public String getName() {
		return PostInitializationEvent.class.getSimpleName();
	}

}
