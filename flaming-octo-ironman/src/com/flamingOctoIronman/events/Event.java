package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface requires that events have certain methods that are required to ensure proper subscribing and publishing
 * of the <code>Event</code>
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
	
	public void subscribe(Object subscriber) {
		handlers.add(subscriber);
		
	}
	
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods;
		for(Object subscriber : handlers){
			if(subscriber instanceof Class){
				methods = ((Class) subscriber).getMethods();
			} else{
				methods = subscriber.getClass().getMethods();
			}
			for(Method method : methods){
				if(method.isAnnotationPresent(annotationClass)){
					if(compareNames(method)){
						if(subscriber instanceof Class){
							System.out.println(String.format("%s: %s", getName(), ((Class) subscriber).getSimpleName()));
						} else{
							System.out.println(String.format("%s: %s", getName(), subscriber.getClass().getSimpleName()));
						}
						method.invoke(subscriber);
					}
				}
			}
		}
		
	}

	public void unsubscribe(Object subscriber) {
		handlers.remove(subscriber);
		
	}
	
	public abstract String getName();
	
	public abstract boolean compareNames(Method m);
}
