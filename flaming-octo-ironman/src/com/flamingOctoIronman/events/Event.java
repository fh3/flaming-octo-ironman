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
	public boolean hasPendingEvents();
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public void subscribe(Object subscriber);
	public void unsubscribe(Object subscriber);
}
