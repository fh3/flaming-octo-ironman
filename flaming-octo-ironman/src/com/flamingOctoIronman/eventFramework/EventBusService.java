package com.flamingOctoIronman.eventFramework;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

/**
 * This class is used to handle the subscribing and publishing of events. It provides the basic framework
 * to create an event bus for a given module.
 * @author Quint
 *
 */
public abstract class  EventBusService<T extends Event> {
	private ServiceLoader<T> loader;	//Loads all core events
	private HashMap<String, T> eventMap;
	
	/**
	 * @param <T>
	 * @param event A service loader with the events loaded
	 */
	public EventBusService(ServiceLoader<T> loader){
		this.loader = (ServiceLoader<T>) loader;
		this.eventMap = new HashMap<String, T>();
		T event;
		Iterator<T> iterator = loader.iterator();
		while(iterator.hasNext()){
			event = iterator.next();
			eventMap.put(event.getName(), event);
		}
	}
	
	public Iterator<T> getEventIterator(){
		return loader.iterator();
	}
	
	/**
	 * Subscribes an object to a core event.
	 * @param subscriber <code>Object</code> that is subscribing to an event.
	 */
	public void subscribe(Object subscriber){
		//Temporary variables
		Method[] methods;
		Event event;
		//Loop through the CoreEvents
		Iterator<T> iterator = loader.iterator();
		while(iterator.hasNext()){
			event = iterator.next();
			methods = subscriber.getClass().getMethods();
			//Loop through all the methods in the subscriber
			for(Method method : methods){
				//If the given method has the EventHandler annotation and the modifier isn't static
				if(method.isAnnotationPresent(CoreEventHandler.class) && !Modifier.isStatic(method.getModifiers())){
					//If one of them is equal to the CoreEvent's class
					if(event.getClass().getSimpleName().equals(method.getAnnotation(CoreEventHandler.class).event())){
						//Subscribe to the event
						event.subscribe(method, subscriber);
					}
				}
			}
		}
	}
	
	/**
	 * Subscribes a class's static method to a core event.
	 * @param subscriber <code>Class</code> that is subscribing to an event.
	 */
	public void subscribe(Class<?> subscriber){
		//Temporary variables
		Method[] methods;
		Event event;
		//Loop through the CoreEvents
		Iterator<T> iterator = loader.iterator();
		while(iterator.hasNext()){
			event = iterator.next();
			methods = subscriber.getMethods();
			//Loop through all the methods in the subscriber
			for(Method method : methods){
				//If the given method has the EventHandler annotation, and the object is static
				if(method.isAnnotationPresent(CoreEventHandler.class) && Modifier.isStatic(method.getModifiers())){
					//If one of them is equal to the CoreEvent's class
					if(event.getClass().getSimpleName().equals(method.getAnnotation(CoreEventHandler.class).event())){
						//Subscribe to the event
						event.subscribe(method, subscriber);
					}
				}
			}
		}
	}
	/**
	 * Publishes a core event.
	 * @param event <code>Class</code> simple name of the event that is being published.
	 */
	public void publish(Class<? extends Event> event){
		Event iteratorEvent;	//Variable to hold CoreEvents from the loader's iterator
		Iterator<? extends Event> iterator = loader.iterator();
		while(iterator.hasNext()){		//While there's an event left in the loader's iterator
			iteratorEvent = iterator.next();	//Get the next CoreEvent
			if(iteratorEvent.getClass() == event){	//If the iteratorEvent is an instance of event
				try {
					iteratorEvent.publish();		//Then try and publish the event
				} catch (Exception e) {	//Otherwise catch the exception
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Returns the annotation class associated with this <code>EventBusService</code>
	 * @return
	 */
	public abstract Class getHandlerAnnotation();
}
