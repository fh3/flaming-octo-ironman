package com.flamingOctoIronman.eventFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.flamingOctoIronman.events.coreEvents.CoreEvent;

/**
 * This class is the framework for all events. It provides methods to subscribe, unsubscribe, and publish events.
 * 
 * @author Quint
 * @see CoreEvent
 */
public abstract class Event {
	private List<Object> handlers;
	private Class annotationClass;
	
	public Event(Class annotation){
		handlers = new ArrayList<Object>();
		annotationClass = annotation;
	}
	
	/**
	 * Subscribe to the event.
	 * @param subscriber Object to subscribe
	 */
	public void subscribe(Object subscriber) {
		handlers.add(subscriber);
		
	}
	
	/**
	 * Publishes the event to all subscribers.
	 * @throws IllegalAccessException This should never be thrown
	 * @throws IllegalArgumentException This should never be thrown
	 * @throws InvocationTargetException This should never be thrown
	 */
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods;
		for(Object subscriber : handlers){
			if(subscriber instanceof Class){
				methods = ((Class<?>) subscriber).getMethods();
			} else{
				methods = subscriber.getClass().getMethods();
			}
			for(Method method : methods){
				if(method.isAnnotationPresent(annotationClass)){
					if(compareNames(method)){
						if(subscriber instanceof Class){
							System.out.println(String.format("%s: %s", getName(), ((Class<?>) subscriber).getSimpleName()));
						} else{
							System.out.println(String.format("%s: %s", getName(), subscriber.getClass().getSimpleName()));
						}
						method.invoke(subscriber);
					}
				}
			}
		}
		
	}

	/**
	 * Removes the object from the list of subscribers; the object will not get notified when the event is published.
	 * @param subscriber Object to unsubscribe from the event
	 */
	public void unsubscribe(Object subscriber) {
		handlers.remove(subscriber);
		
	}
	
	/**
	 * Returns the simplified name of the class.
	 * @return the simplified name of the class.
	 */
	public abstract String getName();
	
	/**
	 * Compares the name of the class to the annotation on the passed method.
	 * @param m The method to compare
	 * @return True if the name of the class is the same as the method's annotation, otherwise false
	 */
	public abstract boolean compareNames(Method m);
}
