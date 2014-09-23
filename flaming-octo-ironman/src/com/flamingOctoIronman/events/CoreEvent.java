package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an abstract implementation of <code>Event</code>. It provides all methods required by <code>Event</code>
 * except for <code>compareClass()</code>.
 * @author Quint
 *
 */
public abstract class CoreEvent implements Event{
	private List<Object> handlers;
	
	public CoreEvent(){
		handlers = new ArrayList<Object>();
	}
	
	@Override
	public void subscribe(Object subscriber) {
		handlers.add(subscriber);
	}
	
	@Override
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods;
		for(Object subscriber : handlers){
			methods = subscriber.getClass().getMethods();
			for(Method method : methods){
				if(method.isAnnotationPresent(CoreEventHandler.class)){
					if(method.getAnnotation(CoreEventHandler.class).event().equals(getName())){
						System.out.println(String.format("%s: %s", getName(), subscriber.getClass().getSimpleName()));
						method.invoke(subscriber);
					}
				}
			}
		}
		
	}

	@Override
	public void unsubscribe(Object subscriber) {
		handlers.remove(subscriber);
		
	}
}
