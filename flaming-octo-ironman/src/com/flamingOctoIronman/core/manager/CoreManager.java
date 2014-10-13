package com.flamingOctoIronman.core.manager;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.framework.manager.Manager;


/**
 * This class is used to mark CoreManagers as Managers.
 * <br>
 * <br>
 * To use this:
 * <br>
 * Create a class that extends this one
 * <br>
 * Add the class to the class manifest 
 * <br>
 * <br>
 * All classes that follow the above instructions will be created during the {@link FlamingOctoIronman#init()} stage of 
 * the game's cycle. All correctly annotated methods in the manager will be registered for their respective events.
 * @author Quint
 *
 */
public abstract class CoreManager extends Manager{
	/**
	 * Nothing to see here, move along.
	 */
	public CoreManager(){
		
	}
}
