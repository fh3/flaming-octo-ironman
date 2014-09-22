package com.flamingOctoIronman.coreSystems.ResourceManager;

import com.flamingOctoIronman.events.CoreEventHandler;

/**
 * This is the main class that loads and unloads files.
 * @author Quint
 *
 */

public class ResourceManager {
	
	/**
	 * This method runs whatever needs to be run at the initialization stage of the game's life.
	 */
	@CoreEventHandler(event = "InitializationEvent")
	public void initialization() {
		System.out.println("Handled");
	}

}
