package com.flamingOctoIronman;

import com.flamingOctoIronman.coreSystems.ResourceManager.ResourceManager;
import com.flamingOctoIronman.events.coreEvents.CoreEventBusService;
import com.flamingOctoIronman.events.coreEvents.GameLoopEvent;
import com.flamingOctoIronman.events.coreEvents.InitializationEvent;
import com.flamingOctoIronman.events.coreEvents.ShutDownEvent;
import com.flamingOctoIronman.events.coreEvents.StartUpEvent;
import com.flamingOctoIronman.timer.TickCalculator;
import com.flamingOctoIronman.timer.Timer;

/**
 * The game engine's main class. Contains the main loop and is what is first created when the game is ran.
 */
public class FlamingOctoIronman implements Runnable{
	//Variables		//It doesn't really matter whether or not these are static because this is a singleton class
	
	//Instance/most important variables
	private static FlamingOctoIronman instance;	//The sole instance of the game
	private static boolean running = false;	//Whether or not the game is currently running
	private static Thread instanceThread = null;	//The instance of the thread the game is running in (not really sure if I need this)
	
	//Timing stuff
	private static float frequency = 60;	//The frequency in Hertz that the game will be running at
	private float period = 1 / frequency;	//The game's period
	
	//Event stuff
	private CoreEventBusService coreBus;
	
	//Managers/Subsystems
	private ResourceManager resourceManager;
	
	/**
	 * Private, as there should only ever be one instance of this class.
	 */
	private FlamingOctoIronman(){
		
	}
	
	/**
	 * Starts the game's life
	 * @return A result code based on how the game exited
	 */
	private int startGame(){
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
	private void init(){
		coreBus = CoreEventBusService.getInstance();	//Ensures that an EventBusService instance is created
		resourceManager = new ResourceManager();
		coreBus.subscribeCore(resourceManager);
		coreBus.subscribeCore(Timer.class);
		coreBus.publishCore(InitializationEvent.class);
	}
	
	/**
	 * Start up of the game
	 */
	private void startUp(){
		coreBus.publishCore(StartUpEvent.class);
	}
	
	/**
	 * The main game loop
	 */
	private void gameLoop(){
		//Variables
		long waitPeriodTime = (long) (1000 * period); 	//The game's period in millisecond
		long previousTime = 0;	//The time the last frame ended
		long waitTime = 0;	//Actual time to wait
		long overtime = 0;	//Time that the cycle ran over/under
		
		running = true;	//Start the engine
		previousTime = System.currentTimeMillis();	//Get the current time in ms from the CPU
		
		//Main game loop
		while(running){
			coreBus.publishCore(GameLoopEvent.class);	//Publish the ticking event
			//Calculate sleep time stuff
			waitTime = waitPeriodTime - (System.currentTimeMillis() - previousTime) + overtime; //The time to sleep equals the FPS wait time minus the time the last frame took plus the overtime
			previousTime = System.currentTimeMillis();	//Update the next frame's previous time to the current time
			
			//If there is time left in the frame, sleep
			if(waitTime < 0){
				//Sleep
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
					running = false;	//End the engine if an exception is thrown
				}
			} else{	//Otherwise, calculate overtime and don't sleep (hurry to catch up) until the engine has caught up
				overtime = waitTime; //Overtime is set to the remaining time
				while((-1 * overtime) >= waitPeriodTime){
					coreBus.publishCore(GameLoopEvent.class);	//Publish the ticking event
					overtime =+ waitPeriodTime;	//Add the standard tick time to overtime
				}
				coreBus.publishCore(GameLoopEvent.class);
				try {
					Thread.sleep(TickCalculator.getInstance().getSleepTimer());
				} catch (InterruptedException e) {
					e.printStackTrace();
					running = false;
				}
			}			
		}
	}
	
	/**
	 * Shut down of the game
	 */
	private void shutDown(){
		coreBus.publishCore(ShutDownEvent.class);
	}
	
	/**
	 * Called when the game exits
	 */
	private void exit(){
		
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
	
	/**
	 * Shuts the game down peacefully
	 */
	public static void stopGame(){
		running = false;
	}
}
