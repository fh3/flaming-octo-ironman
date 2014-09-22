package com.flamingOctoIronman;

import com.flamingOctoIronman.coreSystems.ResourceManager.ResourceManager;
import com.flamingOctoIronman.events.CoreEvent;
import com.flamingOctoIronman.events.EventBusService;

public class FlamingOctoIronman {
	/**
	 * This uses the singleton design pattern
	 */
	private static FlamingOctoIronman instance;
	private FlamingOctoIronman(){
		instance = this;
	}
	public int startGame(){
		init();
		startUp();
		gameLoop();
		shutDown();
		exit();
		return 0;
	}
	private void init(){
		EventBusService eventBusService = EventBusService.getInstance();
		ResourceManager rm = new ResourceManager();
		EventBusService.subscribeCore(rm);
	}
	private void startUp(){
		
	}
	private static void gameLoop(){
		
	}
	private static void shutDown(){
		
	}
	private static void exit(){
		
	}
	public static FlamingOctoIronman getInstance(){
		if(instance == null){
			instance = new FlamingOctoIronman();
		}
		return instance;
	}
}
