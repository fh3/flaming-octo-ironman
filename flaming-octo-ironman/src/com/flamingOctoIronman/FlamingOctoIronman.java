package com.flamingOctoIronman;

import com.flamingOctoIronman.events.EventBus;

public class FlamingOctoIronman {
	/**
	 * This is the game instance itself, the top level class
	 */
	private static FlamingOctoIronman instance;
	private static EventBus eventBus;
	public FlamingOctoIronman(){
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
		eventBus = new EventBus();
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
		return instance;
	}
}
