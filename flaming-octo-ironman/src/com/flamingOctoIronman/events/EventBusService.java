package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ServiceLoader;

public class EventBusService {
	/**
	 * This is a singleton class
	 */
	private static EventBusService instance;	//Sole instance of this class
	private static ServiceLoader<CoreEvent> loader;
	
	private EventBusService(){
		loader = ServiceLoader.load(CoreEvent.class);
	}
	
	/**
	 * Create an instance of this class if it doesn't exist then return said existance
	 * @return
	 */
	public static EventBusService getInstance(){
		if(instance == null){
			instance = new EventBusService();
		}
		return instance;
	}
	
	/**
	 * Subscribe an object to a core event
	 * @param subscriber
	 */
	public static void subscribeCore(Object subscriber){
		//Temp variables
		Method[] methods;
		CoreEvent event;
		//Loop through the CoreEvents
		Iterator<CoreEvent> iterator = loader.iterator();
		while(iterator.hasNext()){
			event = iterator.next();
			methods = subscriber.getClass().getMethods();
			//Loop through all the methods in the subscriber
			for(Method method : methods){
				//If the given method has the EventHandler annotation
				if(method.isAnnotationPresent(CoreEventHandler.class)){
					//If one of them is equal to the CoreEvent's class
					if(event.getClass().getSimpleName().equals(method.getAnnotation(CoreEventHandler.class).event())){
						//Subscribe to the event
						event.subscribe(subscriber);
					}
				}
			}
		}
	}
	/**
	 * Publish a core event
	 * @param event
	 */
	public static void publishCore(Class event){
		for(Iterator<CoreEvent> iterator = loader.iterator(); iterator.hasNext();){
			CoreEvent iter = iterator.next();
			if(iter.getClass() == event){
				try {
					iter.publish();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
