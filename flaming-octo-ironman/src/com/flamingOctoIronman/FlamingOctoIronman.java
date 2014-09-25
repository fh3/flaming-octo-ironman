package com.flamingOctoIronman;

import com.flamingOctoIronman.coreSystems.ResourceManager.ResourceManager;
import com.flamingOctoIronman.events.EventBusService;
import com.flamingOctoIronman.events.GameLoopEvent;
import com.flamingOctoIronman.events.InitializationEvent;
import com.flamingOctoIronman.events.ShutDownEvent;
import com.flamingOctoIronman.events.StartUpEvent;
import com.flamingOctoIronman.timer.TickCalculator;
import com.flamingOctoIronman.timer.Timer;

public class FlamingOctoIronman implements Runnable{
	/**
	 * This uses the singleton design pattern
	 */
	private static FlamingOctoIronman instance;	//The sole instance of the game
	private static boolean running = false;	//Whether or not the game is currently running
	private static Thread instanceThread = null;	//The instance of the thread the game is running in (not really sure if I need this)
<<<<<<< HEAD
	private static float frequency = 60;	//The frequency in Hertz that the game will be running at
	private static float period = 1 / frequency;	//The game's period
	private static long waitPeriodTime = (long) (1000 * period); 	//The game's period in millisecond
	private static long previousTime = 0;	//The time the last frame ended
	private static long waitTime = 0;	//Actual time to wait
	private static long overtime = 0;	//Time that the cycle ran over/under
=======
>>>>>>> branch 'master' of https://github.com/fh3/flaming-octo-ironman.git
	
	private FlamingOctoIronman(){
		
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
		running = true;	//Start the engine
		previousTime = System.currentTimeMillis();	//Get the current time in ms from the CPU
		while(running){
<<<<<<< HEAD
			EventBusService.publishCore(GameLoopEvent.class);	//Publish the ticking event
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
					EventBusService.publishCore(GameLoopEvent.class);	//Publish the ticking event
					overtime =+ waitPeriodTime;	//Add the standard tick time to overtime
				}
=======
			EventBusService.publishCore(GameLoopEvent.class);
			try {
				Thread.sleep(TickCalculator.getInstance().getSleepTimer());
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
>>>>>>> branch 'master' of https://github.com/fh3/flaming-octo-ironman.git
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
