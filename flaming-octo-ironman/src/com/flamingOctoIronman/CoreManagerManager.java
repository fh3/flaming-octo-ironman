package com.flamingOctoIronman;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.flamingOctoIronman.events.coreEvents.CoreEvent;
import com.flamingOctoIronman.events.coreEvents.CoreEventBusService;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

public class CoreManagerManager {
	private static CoreManagerManager instance;
	
	private ServiceLoader<CoreManager> classLoader;
	private HashMap<String, CoreManager> classMap;
	
	private CoreManagerManager(){
		
	}
	
	public static CoreManagerManager getInstance(){
		if(instance == null){
			instance = new CoreManagerManager();
		}
		return instance;
	}
	
	public void initialize(CoreEventBusService busService){
		classLoader = ServiceLoader.load(CoreManager.class);
		classMap = new HashMap<String, CoreManager>();
		CoreManager c;
		Iterator<CoreManager> iterator = classLoader.iterator();
		while(iterator.hasNext()){
			c = iterator.next();
			classMap.put(c.getName(), c);
		}
		
		CoreEvent event;
		Iterator<CoreEvent> iterator2= busService.getEventIterator();
		while(iterator2.hasNext()){	//For every event in the CoreEventBusService,
			event = iterator2.next();
			for(CoreManager manager : classMap.values()){	//For each manager in the manager map
				for(Method method : manager.getClass().getMethods()){	//For each method in each manager
					if(method.isAnnotationPresent(CoreEventHandler.class)){	//If the name of the annotation on the method is equal to the event's handler
						if(event.compareNames(method)){	//If the event parameter is equal to the event's name
							event.subscribe(method, manager);	//Then subscribe the method and the object to the event
						}
					}
				}
			}
		}
	}
	
	public CoreManager getCoreManager(String simplifiedName){
		return classMap.get(simplifiedName);
	}
	
}
