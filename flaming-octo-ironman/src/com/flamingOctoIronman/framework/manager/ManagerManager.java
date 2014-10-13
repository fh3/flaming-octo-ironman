package com.flamingOctoIronman.framework.manager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.flamingOctoIronman.framework.event.Event;
import com.flamingOctoIronman.framework.event.EventBusService;

public abstract class ManagerManager<T1 extends Manager, T2 extends Event> {
	
	private EventBusService<T2> busService;
	
	private ServiceLoader<T1> classLoader;
	private Class<T1> toLoad;
	
	private HashMap<String, T1> classMap;
	
	public ManagerManager(EventBusService<T2> busService, Class<T1> toLoad){
		this.busService = busService;
		this.toLoad = toLoad;
	}
		
	public void initialize(){
		classLoader = ServiceLoader.load(toLoad);
		classMap = new HashMap<String, T1>();
		T1 manager;
		Iterator<T1> iterator = classLoader.iterator();
		while(iterator.hasNext()){
			manager = iterator.next();
			classMap.put(manager.getName(), manager);
		}
		
		T2 event;
		Iterator<T2> iterator2 = busService.getEventIterator();
		while(iterator2.hasNext()){	//For every event in the CoreEventBusService,
			event = iterator2.next();
			for(T1 manager2 : classMap.values()){	//For each manager in the manager map
				checkMethodsAndSubscribe(manager2, event);
			}
			
		}
	}
	
	public void postInitialize(){
		T2 event;
		Iterator<T2> iterator = busService.getEventIterator();
		while(iterator.hasNext()){	//For every event in the CoreEventBusService,
			event = iterator.next();
			for(T1 manager : classMap.values()){	//For each manager in the manager map
				if(manager.getSubManagers() != null){
					for(Object object : manager.getSubManagers()){
						checkMethodsAndSubscribe(object, event);
					}
				}
			}
			
		}
	}
	
	
	private void checkMethodsAndSubscribe(Object object, T2 event){
		for(Method method : object.getClass().getMethods()){	//For each method in each manager
			if(method.isAnnotationPresent(busService.getHandlerAnnotation())){	//If the name of the annotation on the method is equal to the event's handler
				if(event.compareNames(method)){	//If the event parameter is equal to the event's name
					event.subscribe(method, object);	//Then subscribe the method and the object to the event
				}
			}
		}
	}
	
	public T1 getManager(String simplifiedName){
		return classMap.get(simplifiedName);
	}
}
