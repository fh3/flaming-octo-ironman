package com.flamingOctoIronman.events;

public interface Event {
	public void addListener(Listener toAdd);
	public void fire();
}
