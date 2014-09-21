package com.flamingOctoIronman.events;

import java.util.HashMap;

public class EventBus {
	//TODO make this class more extendible, currently is crap
	//Warning is suppressed because I don't care what type is passed to the Event object
	@SuppressWarnings("rawtypes")
	private HashMap<String, Event> coreEvents;
	private final String initializerName = InitializationListener.class.getName();
	
	@SuppressWarnings("rawtypes")
	public EventBus(){
		//Initialize variables
		coreEvents = new HashMap<String, Event>(3);
		
		//Put events and names into the HashMap
		coreEvents.put(initializerName, new Event<InitializationListener>());
		coreEvents.put(StartUpListener.class.getName(), new Event<StartUpListener>());
		coreEvents.put(ShutDownListener.class.getName(), new Event<ShutDownListener>());
	}
	
	public <T extends Listener> void RegisterForEvent(T listener){
		switch (listener.getClass().getName()){
			case "com.flamingOctoIronman.events.InitializationListener":
				break;
			default:
				break;
		}
	}
	
	public void RegisterNewCoreEvent(Event e){
		
	}
}
