package com.flamingOctoIronman.core.event;

import java.util.ServiceLoader;

import com.flamingOctoIronman.eventFramework.EventBusService;

/**
 * This event bus is for the engine's core events. Only one instance of this class is ever created.
 * @author Quint Heinecke
 *
 */
public class CoreEventBusService extends EventBusService<CoreEvent> {
	private static CoreEventBusService instance;
	
	private CoreEventBusService() {
		super(ServiceLoader.load(CoreEvent.class));
	}
	
	public static CoreEventBusService getInstance(){
		if(instance == null){
			instance = new CoreEventBusService();
		}
		return instance;
	}

	@Override
	public Class getHandlerAnnotation() {
		return CoreEventHandler.class;
	}

}
