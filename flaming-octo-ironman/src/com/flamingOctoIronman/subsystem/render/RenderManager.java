package com.flamingOctoIronman.subsystem.render;

import com.flamingOctoIronman.core.manager.CoreManager;

/**
 * This package handles everything related to the display.
 * @author Quint Heinecke
 *
 */
public class RenderManager extends CoreManager{
	
	private RenderEngine engine;
	
	public RenderManager(){
		super();	//Call to super
		engine = RenderEngine.getInstance();	//Get the render engien instance
	}

	/**
	 * @return "RenderManager"
	 */
	@Override
	public String getName() {
		return RenderManager.class.getSimpleName();
	}
	
	/**
	 * @return the {@link RenderEngine} instance
	 */
	@Override
	public Object[] getSubManagers(){
		Object[] toReturn = {engine};	//Add the submanagers to a return array
		return toReturn;	//Return the array
	}

}
