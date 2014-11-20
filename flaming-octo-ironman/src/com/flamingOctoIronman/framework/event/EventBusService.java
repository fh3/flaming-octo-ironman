package com.flamingOctoIronman.framework.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * This class is used to handle the subscribing and publishing of events. It provides the basic framework
 * to create an event bus for a given module. Type <code>T</code> has to extend {@link Event}.
 * @author Quint
 *
 */
public class EventBusService<T extends Event> {
	/**
	 * This loads all the <code>Event</code>s. For a class to be loaded, it must extend <code>T</code>, and must be
	 * registered in <code>T</code>'s class manifest.
	 */
	private ServiceLoader<T> loader;
	/**
	 * This {@link HashMap} Stores a key/pair of the simple name of every <code>T</code> and <code>T</code> instance.
	 */
	private HashMap<String, T> eventMap;
	
	/**
	 * Creates a new <code>EventBusService</code>. Initialize references and loop through all the <code>T</code>
	 * in the loader and add them to the <code>eventMap</code>
	 * @param loader A {@link ServiceLoader} instance with all the <code>T</code> classes loaded.
	 */
	public EventBusService(ServiceLoader<T> loader){
		//Initialized references
		this.loader = (ServiceLoader<T>) loader;	//Set the loader reference to the passed argument
		this.eventMap = new HashMap<String, T>();	//Initialized the eventMap
		
		//Loop through all the events in the loader and add them to the eventMap
		T event;	//Create a temporary reference for T instances
		Iterator<T> iterator = loader.iterator();	//Get a new Iterator from the loader
		while(iterator.hasNext()){	//Loop through the iterator while there's still objects remaining
			event = iterator.next();	//Set the temporary reference to the next T instance in the iterator
			eventMap.put(event.getName(), event);	//Add the name of that instance and the instance to the eventMap
		}
	}
	
	/**
	 * Returns a copy of the <code>ServiceLoader</code>'s {@link Iterator}.
	 * @return	A copy of the <code>ServiceLoader</code>'s <code>Iterator</code>
	 */
	public Iterator<T> getEventIterator(){
		return loader.iterator();	//Get a copy of the iterator and return it
	}
	
	/**
	 * Subscribes an <code>Object</code> to the <code>Event</code>s
	 * @param subscriber <code>Object</code> that is subscribing to an event.
	 */
	public void subscribe(Object subscriber){
		//Temporary variables
		Method[] methods = subscriber.getClass().getMethods();	//Get an array of all the methods in the class
		Event event;
		//Loop through the Events
		Iterator<T> iterator = loader.iterator();	//Get a new Iterator
		while(iterator.hasNext()){
			event = iterator.next();	//Get a the next iterator entry
			//Loop through all the methods in the subscriber
			for(Method method : methods){
				//If the given method has the EventHandler annotation and the modifier isn't static
				if(method.isAnnotationPresent(EventHandler.class) && !Modifier.isStatic(method.getModifiers())){
					//If one of them is equal to the Event's class
					if(event.getClass().getSimpleName().equals(method.getAnnotation(EventHandler.class).event())){
						//Subscribe to the event
						event.subscribe(method, subscriber);
					}
				}
			}
		}
	}
	
	/**
	 * Subscribes a class's static methods to the events.
	 * @param subscriber <code>Class</code> that is subscribing to the events.
	 */
	public void subscribe(Class<?> subscriber){
		//Temporary variables
		Method[] methods;
		Event event;
		//Loop through the Events
		Iterator<T> iterator = loader.iterator();
		while(iterator.hasNext()){
			event = iterator.next();
			methods = subscriber.getMethods();
			//Loop through all the methods in the subscriber
			for(Method method : methods){
				//If the given method has the EventHandler annotation, and the object is static
				if(method.isAnnotationPresent(EventHandler.class) && Modifier.isStatic(method.getModifiers())){
					//If one of them is equal to the Event's class
					if(event.getClass().getSimpleName().equals(method.getAnnotation(EventHandler.class).event())){
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
		Event iteratorEvent;	//Variable to hold Events from the loader's iterator
		Iterator<? extends Event> iterator = loader.iterator();
		while(iterator.hasNext()){		//While there's an event left in the loader's iterator
			iteratorEvent = iterator.next();	//Get the next Event
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
