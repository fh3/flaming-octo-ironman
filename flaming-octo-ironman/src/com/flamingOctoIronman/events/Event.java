package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;

/**
 * This interface requires that events have certain methods that are required to ensure proper subscribing and publishing
 * of the <code>Event</code>
 * 
 * @author Quint
 * @see CoreEvent
 */
public interface Event {
	/**
	 * Publish this <code>Event</code> to all of it's subscribers.
	 * @throws IllegalAccessException This should never occur but is checked
	 * @throws IllegalArgumentException This should never occur but is checked
	 * @throws InvocationTargetException This should never occur but is checked
	 */
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	/**
	 * Adds the <code>Object</code> to the list of objects that will get notified when the event is published.
	 * @param subscriber <code>Object</code> that is subscribing to the event
	 */
	public void subscribe(Object subscriber);
	/**
	 * Removes the <code>Object</code> from the list of objects taht will get notified when the event is published
	 * @param subscriber <code>Object</code> to be removed
	 */
	public void unsubscribe(Object subscriber);
	/**
	 * Compares the <code>String</code> to the name of the class that implements this method.
	 * <p>
	 * This should ALWAYS only be implemented by a <code>final</code> class.
	 * @param s simplified name of the <code>Class</code> to compare to.
	 * @return Whether or not the <code>String</code>s are the same
	 */
	public boolean compareClass(String s);
}
