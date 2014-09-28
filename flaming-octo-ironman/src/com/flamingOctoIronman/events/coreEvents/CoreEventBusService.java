package com.flamingOctoIronman.events.coreEvents;

import com.flamingOctoIronman.events.EventBusService;

/**
 * This event bus is for the engine's core events. Only one instance of this class is ever created.
 * @author Quint Heinecke
 *
 */
public class CoreEventBusService extends EventBusService {
	private static CoreEventBusService instance;
	
	private CoreEventBusService() {
		super(CoreEvent.class);
	}
	
	public static CoreEventBusService getInstance(){
		if(instance == null){
			instance = new CoreEventBusService();
		}
		return instance;
	}

}
