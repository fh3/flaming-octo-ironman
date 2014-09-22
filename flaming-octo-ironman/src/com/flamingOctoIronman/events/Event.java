package com.flamingOctoIronman.events;

public interface Event {
	public boolean hasPendingEvents();
	public void publish();
	public void subscribe(Object subscriber);
	public void unsubscribe(Object subscriber);
}
