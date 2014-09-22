package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class InitializationEvent implements CoreEvent {
	private List<Object> handlers;
	
	public InitializationEvent(){
		handlers = new ArrayList<Object>();
	}
	@Override
	public boolean hasPendingEvents() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods;
		for(Object subscriber : handlers){
			methods = subscriber.getClass().getMethods();
			for(Method method : methods){
				if(method.isAnnotationPresent(CoreEventHandler.class)){
					if(method.getAnnotation(CoreEventHandler.class).event().equals(InitializationEvent.class.getSimpleName())){
						method.invoke(subscriber);
					}
				}
			}
		}
		
	}

	@Override
	public void subscribe(Object subscriber) {
		handlers.add(subscriber);
		System.out.println();
	}

	@Override
	public void unsubscribe(Object subscriber) {
		handlers.remove(subscriber);
		
	}
}
