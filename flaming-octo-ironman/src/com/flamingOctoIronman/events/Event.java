package com.flamingOctoIronman.events;

import java.lang.reflect.InvocationTargetException;

public interface Event {
	public boolean hasPendingEvents();
	public void publish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public void subscribe(Object subscriber);
	public void unsubscribe(Object subscriber);
}
