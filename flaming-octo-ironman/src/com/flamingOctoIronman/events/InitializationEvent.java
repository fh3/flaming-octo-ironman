package com.flamingOctoIronman.events;

import java.util.ArrayList;
import java.util.List;

public class InitializationEvent implements Event {
	List<InitializationListener> listeners = new ArrayList<InitializationListener>();

	//Registers the listener
	@Override
	public void addListener(Listener toAdd) {
		listeners.add((InitializationListener) toAdd);		
	}

	//To fire event call this method
	@Override
	public void fire() {
		for(InitializationListener il : listeners){
			il.fire();
		}
	}

}
