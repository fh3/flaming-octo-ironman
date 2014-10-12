package com.flamingOctoIronman.eventFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.debugging.DebuggingManager;
import com.flamingOctoIronman.debugging.StreamManager;
import com.flamingOctoIronman.events.coreEvents.CoreEvent;

/**
 * This class is the framework for all events. It provides methods to subscribe, unsubscribe, and publish events.
 * 
 * @author Quint
 * @see CoreEvent
 */
public abstract class Event {
	private StreamManager streams = FlamingOctoIronman.getInstance().getStreamManager(); //Use this to make things a little easier to read
	private ArrayList<Method> methodList;
	private ArrayList<Object> objectList;
	
	public Event(Class annotation){
		methodList = new ArrayList<Method>();
		objectList = new ArrayList<Object>();
	}
	
	/**
	 * Subscribe to the event.
	 * @param subscriber Object to subscribe
	 */
	public void subscribe(Method subscriberMethod, Object subscriberObject) {
		methodList.add(subscriberMethod);
		objectList.add(subscriberObject);
	}
	
	/**
	 * Publishes the event to all subscribers.
	 * @throws IllegalAccessException This should never be thrown
	 * @throws IllegalArgumentException This should never be thrown
	 * @throws InvocationTargetException This should never be thrown
	 */
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(int i = 0; i < methodList.size(); i++){
			streams.println(objectList.get(i).getClass().getSimpleName() + ": " + this.getName());
			methodList.get(i).invoke(objectList.get(i));
		}
	}

	/**
	 * Removes the object from the list of subscribers; the object will not get notified when the event is published.
	 * @param subscriber Object to unsubscribe from the event
	 */
	public void unsubscribe(Object subscriber) {
		methodList.remove(objectList.indexOf(subscriber));
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
