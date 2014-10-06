package com.flamingOctoIronman.coreSystems.ResourceManager;

import com.flamingOctoIronman.Manager;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

/**
 * This is the main class that loads and unloads files.
 * @author Quint
 *
 */

public class ResourceManager extends Manager{
	
	/**
	 * This method runs whatever needs to be run at the initialization stage of the game's life.
	 */
	@CoreEventHandler(event = "InitializationEvent")
	public void initialization() {
	}
	@CoreEventHandler(event = "StartUpEvent")
	public void startUpEvent(){
		
	}

}
