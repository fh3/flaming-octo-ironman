package com.flamingOctoIronman.core.manager;

import com.flamingOctoIronman.core.event.CoreEvent;
import com.flamingOctoIronman.core.event.CoreEventBusService;
import com.flamingOctoIronman.managerFramework.ManagerManager;

/**
 * This class handles all of the game's {@link CoreManager}s. It is used to create an instance of all managers, and
 * to register them with the game's {@link CoreEventBusService}
 * @author Quint
 *
 */
public class CoreManagerManager extends ManagerManager<CoreManager, CoreEvent>{
	private static CoreManagerManager instance;
	
	private CoreManagerManager(CoreEventBusService coreBus){
		super(coreBus, CoreManager.class);
	}
	
	public static CoreManagerManager getInstance(CoreEventBusService coreBus){
		if(instance == null){
			instance = new CoreManagerManager(coreBus);
		}
		return instance;
	}
}
