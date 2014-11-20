package com.flamingOctoIronman.core.event;

import java.util.ServiceLoader;

import com.flamingOctoIronman.framework.event.EventBusService;

/**
 * This {@link EventBusService} is for the engine's {@link CoreEvent}s. Only one instance of this class is ever created (Singleton design
 * pattern).
 * @author Quint Heinecke
 *
 */
public class CoreEventBusService extends EventBusService<CoreEvent> {
	/**
	 * This sole instance of this class
	 */
	private static CoreEventBusService instance;
	
	/**
	 * Creates a new instance, provides <code>EventBusService</code> with a new loaded <code>ServiceLoader&lt;CoreEvent&gt;</code>
	 */
	private CoreEventBusService() {
		super(ServiceLoader.load(CoreEvent.class));	//Call super with a new ServiceLoader<CoreEvent>
	}
	
	/**
	 * Gets the instance of this class.
	 * @return The sole instance of this class
	 */
	public static CoreEventBusService getInstance(){
		if(instance == null){	//If the instance reference is null
			instance = new CoreEventBusService();	//Then set it to a new instance
		}
		return instance;	//Return the instance
	}
}
