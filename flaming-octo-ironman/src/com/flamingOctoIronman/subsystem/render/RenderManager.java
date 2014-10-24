package com.flamingOctoIronman.subsystem.render;

import com.flamingOctoIronman.core.manager.CoreManager;

/**
 * This package handles everything related to the display.
 * @author Quint Heinecke
 *
 */
public class RenderManager extends CoreManager{
	
	private RenderEngine2 engine;
	
	public RenderManager(){
		super();	//Call to super
		engine = RenderEngine2.getInstance();	//Get the render engine instance
	}

	/**
	 * @return "RenderManager"
	 */
	@Override
	public String getName() {
		return RenderManager.class.getSimpleName();
	}
	
	/**
	 * @return The {@link RenderEngine} instance
	 */
	@Override
	public Object[] getSubManagers(){
		return new Object[]{engine};	//Return the array
	}

}
