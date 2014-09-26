package com.flamingOctoIronman.events;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * This class is used to handle the subscribing and publishing of events. It is a singleton class,
 * call <code>EventBusService.getInstance()</code> to create and get an instance of this class.
 * @author Quint
 *
 */
public abstract class EventBusService<T extends Event> {
	private ServiceLoader<T> loader;	//Loads all core events
	private T nullInstance;
	
	/**
	 * Standard constructor, set to private to only allow for one instance of this class to be created.
	 */
	@SuppressWarnings("unchecked")
	public EventBusService(T nullInstance){
		loader = (ServiceLoader<T>) ServiceLoader.load(nullInstance.getClass());	//Loads all events that implement interface CoreEvent.class
	}
	
	/**
	 * Subscribes an object to a core event.
	 * @param subscriber <code>Object</code> that is subscribing to an event.
	 */
	public void subscribeCore(Object subscriber){
		//Temporary variables
		Method[] methods;
		CoreEvent event;
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
						event.subscribe(subscriber);
					}
				}
			}
		}
	}
	
	/**
	 * Subscribes a class's static method to a core event.
	 * @param subscriber <code>Class</code> that is subscribing to an event.
	 */
	public void subscribeCore(Class subscriber){
		//Temporary variables
		Method[] methods;
		CoreEvent event;
		//Loop through the CoreEvents
		Iterator<CoreEvent> iterator = loader.iterator();
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
						event.subscribe(subscriber);
					}
				}
			}
		}
	}
	/**
	 * Publishes a core event.
	 * @param event <code>Class</code> simple name of the event that is being published.
	 */
	public void publishCore(Class event){
		Iterator<CoreEvent> iterator = loader.iterator();	//Gets the iterator from the loader
		CoreEvent iteratorEvent;	//Variable to hold CoreEvents from the loader's iterator
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
}
