package com.flamingOctoIronman.core.manager;

import com.flamingOctoIronman.core.event.CoreEvent;
import com.flamingOctoIronman.core.event.CoreEventBusService;
import com.flamingOctoIronman.framework.manager.ManagerManager;

/**
 * This class handles all of the game's {@link CoreManager}s. It is used to create an instance of all managers, and
 * to register them with the game's {@link CoreEventBusService}. This class implements the singleton design pattern, call
 * {@link CoreManagerManager#getInstance(CoreEventBusService)
 * @author Quint
 *
 */
public class CoreManagerManager extends ManagerManager<CoreManager>{
	/**
	 * This class's instance
	 */
	private static CoreManagerManager instance;
	
	/**
	 * Creates a new instance of this class.
	 * @param coreBus	The game's <code>CoreEventBusService</code>
	 */
	private CoreManagerManager(CoreEventBusService coreBus){
		super(coreBus, CoreManager.class);	//Call to super
	}
	/**
	 * Get the instance of the <code>CoreManagerManager</code>. 
	 * @param coreBus	The game's instance of <code>CoreEventBusService</code>. This can be null after the first time this method is called.
	 * @return	The <code>CoreManagerManager</code> instance.
	 */
	public static CoreManagerManager getInstance(CoreEventBusService coreBus){
		if(instance == null){	//If the instance hasn't been created,
			instance = new CoreManagerManager(coreBus);	//Create it
		}
		return instance;	//Return the instance
	}
}
