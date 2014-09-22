package com.flamingOctoIronman.coreSystems.ResourceManager;

import com.flamingOctoIronman.events.CoreEventHandler;

public class ResourceManager {

	@CoreEventHandler(event = "InitializationEvent")
	public void initialization() {
		System.out.println("Handled");
	}

}
