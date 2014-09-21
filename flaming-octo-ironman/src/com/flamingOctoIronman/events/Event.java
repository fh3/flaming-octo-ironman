package com.flamingOctoIronman.events;

import java.util.ArrayList;
import java.util.List;

public class Event<T extends Listener> {
	List<T> listeners;
	Class c;
	
	public Event(){
		listeners = new ArrayList<T>();
		System.out.println();
	}
	//Registers the listener
	public void addListener(T toAdd) {
		listeners.add(toAdd);		
	}

	//To fire event call this method
	public void fire() {
		for(T listener : listeners){
			listener.fire();
		}
	}
	
	public <T> String getListenerName(){
		return 
	}
}
