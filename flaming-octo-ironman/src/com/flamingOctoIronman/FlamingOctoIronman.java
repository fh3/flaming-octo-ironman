package com.flamingOctoIronman;

import com.flamingOctoIronman.coreSystems.ResourceManager.ResourceManager;
import com.flamingOctoIronman.events.EventBusService;
import com.flamingOctoIronman.events.GameLoopEvent;
import com.flamingOctoIronman.events.InitializationEvent;
import com.flamingOctoIronman.events.ShutDownEvent;
import com.flamingOctoIronman.events.StartUpEvent;
import com.flamingOctoIronman.timer.Timer;

public class FlamingOctoIronman implements Runnable{
	/**
	 * This uses the singleton design pattern
	 */
	private static FlamingOctoIronman instance;	//The sole instance of the game
	private static boolean running = false;	//Whether or not the game is currently running
	private static Thread instanceThread = null;	//The instance of the thread the game is running in (not really sure if I need this)
	private static float frequency = 60;	//The frequency in Hertz that the game will be running at
	private static float period = 1 / frequency;	//The game's period
	
	private FlamingOctoIronman(){
		instance = this;
	}
	/**
	 * Starts the game's life
	 * @return A result code based on how the game exited
	 */
	public static int startGame(){
		init();
		startUp();
		gameLoop();
		shutDown();
		exit();
		return 0;
	}
	/**
	 * Initialization of the game
	 */
	private static void init(){
		EventBusService.getInstance();	//Ensures that an EventBusService instance is created
		ResourceManager rm = new ResourceManager();
		EventBusService.subscribeCore(rm);
		EventBusService.subscribeCore(Timer.class);
		EventBusService.publishCore(InitializationEvent.class);
	}
	/**
	 * Start up of the game
	 */
	private static void startUp(){
		EventBusService.publishCore(StartUpEvent.class);
	}
	/**
	 * The main game loop
	 */
	private static void gameLoop(){
		running = true;
		while(running){
			EventBusService.publishCore(GameLoopEvent.class);
			try {
				Thread.sleep((long)(period * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}
	}
	/**
	 * Shut down of the game
	 */
	private static void shutDown(){
		EventBusService.publishCore(ShutDownEvent.class);
	}
	/**
	 * Called when the game exits
	 */
	private static void exit(){
		
	}
	/**
	 * Returns the game's instance. Creates and returns the instance if it is null.
	 * 
	 * @return The instance of the game.
	 */
	public static FlamingOctoIronman getInstance(){
		if(instance == null){
			instance = new FlamingOctoIronman();
		}
		return instance;
	}
	/**
	 * Starts the game and the game's thread.
	 * 
	 * @param args Arguments passed from the command line
	 * @see Thread
	 */
	public static void main(String args[]){
		instanceThread = new Thread(FlamingOctoIronman.getInstance());
		instanceThread.start();
	}
	/**
	 * Starts the thread and calls {@link #startGame()}
	 * 
	 * @see {@link #gameStart()}
	 */
	@Override
	public void run() {
		startGame();
	}
}