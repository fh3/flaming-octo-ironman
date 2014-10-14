package com.flamingOctoIronman.framework.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.event.CoreEvent;
import com.flamingOctoIronman.debugging.DebuggingManager;
import com.flamingOctoIronman.debugging.StreamManager;
import com.flamingOctoIronman.debugging.Verbosity;

/**
 * This class is the framework for all events. It provides methods to subscribe, unsubscribe, and publish events.
 * 
 * @author Quint
 * @see CoreEvent
 */
public abstract class Event {
	/**
	 * This reference is to make it easier to access the stream manager.
	 */
	private StreamManager streams = FlamingOctoIronman.getInstance().getStreamManager(); //Use this to make things a little easier to read
	/**
	 * Holds a list of methods that will be called on the event
	 */
	private ArrayList<Method> methodList;
	/**
	 * Holds a list of objects to call the above methods on
	 */
	private ArrayList<Object> objectList;
	/**
	 * If {@link Verbosity#MEDIUM} or {@link Verbosity#HIGH} A line will be added to the output whenever a subscribed
	 * method is called.
	 * 
	 * @see Verbosity
	 */
	private Verbosity level = Verbosity.MEDIUM;
	
	/**
	 * Creates a new event
	 */
	public Event(){
		methodList = new ArrayList<Method>();	//Initializes the ArrayList
		objectList = new ArrayList<Object>();	//Initializes the ArrayList
	}
	
	/**
	 * Subscribes the {@link Method} and it's respective {@link Object} to the event.
	 * @param subscriberMethod	The <code>Method</code> to subscribe
	 * @param subscriberObject	The <code>Object</code> to subscribe
	 */
	public void subscribe(Method subscriberMethod, Object subscriberObject) {
		methodList.add(subscriberMethod);	//Add the Method to the ArrayList
		objectList.add(subscriberObject);	//Add the Object to the ArrayList
	}
	
	/**
	 * Publishes the event to all subscribers.
	 * @throws IllegalAccessException Thrown if the <code>Method</code> is inaccessible
	 * @throws IllegalArgumentException Thrown if the <code>Method</code> takes any arguments
	 * @throws InvocationTargetException Thrown if the subscribed <code>Method</code> can't be performed on it's <code>Object</code>
	 */
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(int i = 0; i < methodList.size(); i++){	//Loop through the ArrayLists until the end is reached
			if(getVerbosity() != Verbosity.HIGH){	//If the verbosity of the object is not high,
				streams.println(objectList.get(i).getClass().getSimpleName() + ": " + this.getName());	//Print out a line in the format "ObjectName: EventName"
			}
			methodList.get(i).invoke(objectList.get(i));	//Invoke the method on the object
		}
	}

	/**
	 * Removes the <code>Object</code> and it's <code>Method</code> from the list. Warning: if the <code>Object</code>
	 * has more than one subscriber <code>Method</code>, the wrong subscriber <code>Object</code> could be removed.
	 * @param subscriberObject	The <code>Object</code> to unsubscribe
	 * @param subscriberMethod	The <code>Method</code> to unsubscribe
	 */
	public void unsubscribe(Object subscriberObject, Method subscriberMethod) {
		methodList.remove(methodList.indexOf(subscriberMethod));	//Remove the subscribed method
		objectList.remove(objectList.indexOf(subscriberObject));	//Remove the subscribed object
	}
	
	/**
	 * Calls {@link Class#getSimpleName()} on the class. Returns the simple name of the class.
	 * @return the simple name of the class.
	 */
	public abstract String getName();
	
	/**
	 * Compares the name of the class to the annotation on the passed method.
	 * @param m The <code>Method</code> to compare
	 * @return True if the name of the class is the same as the method's annotation, otherwise false
	 */
	public abstract boolean compareNames(Method m);
		
	/**
	 * Returns the verbosity of the <code>Event</code>
	 * @return the {@link Verbosity} of the <code>Event</code>
	 */
	public Verbosity getVerbosity(){
		return this.level;
	}

	/**
	 * Sets the verbosity of the <code>Event</code>
	 * @param level	the {@link Verbosity} level to set the <code>Event</code> to
	 */
	public void setVerbosity(Verbosity level) {
		this.level = level;
	}
}
