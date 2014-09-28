package com.flamingOctoIronman.events;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

/**
 * This class is used to handle the subscribing and publishing of events. It provides the basic framework
 * to create an event bus for a given module.
 * @author Quint
 *
 */
public abstract class EventBusService {
	private ServiceLoader<Event> loader;	//Loads all core events
	
	/**
	 * This constructor loads all the events into the <code>ServiceLoader</code>.
	 * @param event Any <code>Class</code> that extends Event, this needs to extends the <code>Event</code> class
	 */
	@SuppressWarnings("unchecked")
	public EventBusService(Class<? extends Event> event){
		loader = (ServiceLoader<Event>) ServiceLoader.load(event);	//Loads all events that implement interface CoreEvent.class
	}
	
	/**
	 * Subscribes an object to a core event.
	 * @param subscriber <code>Object</code> that is subscribing to an event.
	 */
	public void subscribeCore(Object subscriber){
		//Temporary variables
		Method[] methods;
		Event event;
		//Loop through the CoreEvents
		Iterator<Event> iterator = loader.iterator();
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
		Event event;
		//Loop through the CoreEvents
		Iterator<Event> iterator = loader.iterator();
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
		Iterator<Event> iterator = loader.iterator();	//Gets the iterator from the loader
		Event iteratorEvent;	//Variable to hold CoreEvents from the loader's iterator
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
