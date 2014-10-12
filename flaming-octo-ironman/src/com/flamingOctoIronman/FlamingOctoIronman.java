package com.flamingOctoIronman;

import javax.swing.JFrame;

import com.flamingOctoIronman.coreSystems.ResourceManager.ResourceManager;
import com.flamingOctoIronman.debugging.StreamManager;
import com.flamingOctoIronman.events.coreEvents.CoreEventBusService;
import com.flamingOctoIronman.events.coreEvents.GameLoopEvent;
import com.flamingOctoIronman.events.coreEvents.InitializationEvent;
import com.flamingOctoIronman.events.coreEvents.PostInitializationEvent;
import com.flamingOctoIronman.events.coreEvents.ShutDownEvent;
import com.flamingOctoIronman.events.coreEvents.StartUpEvent;
import com.flamingOctoIronman.timer.TickCalculator;
import com.flamingOctoIronman.visualTest.MyWindow;

/**
 * The game engine's main class. Contains the main loop and is what is first created when the game is ran.
 */
public class FlamingOctoIronman implements Runnable{
	//Variables		//It doesn't really matter whether or not these are static because this is a singleton class
	
	//Instance/most important variables
	private static FlamingOctoIronman instance;	//The sole instance of the game
	private static boolean running = false;	//Whether or not the game is currently running
	
	//Timing stuff
	private static float frequency = 60;	//The frequency in Hertz that the game will be running at
	private float period = 1 / frequency;	//The game's period
	
	//Core stuff
	private CoreEventBusService coreBus;
	private CoreManagerManager coreManagerManager;
	
	//Manager
	//This is the one manager that should be in this class. It's here to allow for stream output before the whole 
	//engine starts up
	//The preferred method of referencing this is through the DebuggingManager once it's been created
	private StreamManager streamManager;
	
	private MyWindow window;
	
	//Death
	private static DeathReason reason = null;
	
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
		preinit();
		init();
		postinit();
		startUp();
		gameLoop();
		shutDown();
		exit();
		return 0;
	}
	
	private void preinit(){
		streamManager = new StreamManager();
		streamManager.addStreamToOutput(ResourceManager.getPrintStream("logs/log.txt"));	//I can access ResourceManager here because the method is static, which doesn't require the manager to be initialized for me to use it
		
		streamManager.println("Starting the game");
		
		coreBus = CoreEventBusService.getInstance();	//Ensures that an EventBusService instance is created
		coreManagerManager = CoreManagerManager.getInstance();
	}
	
	/**
	 * Initialization of the game. In this method, the core event bus and managers are initialized, and some 
	 * additional setup may be done here. No cross-module interfacing is allowed, except for static methods.
	 */
	private void init(){
		coreManagerManager.initialize(coreBus);	
		window = new MyWindow();
						
		//Public the event for this method
		coreBus.publish(InitializationEvent.class);
	}
	
	private void postinit(){
		coreBus.publish(PostInitializationEvent.class);
	}
	
	/**
	 * Start up of the game. Cross-module interfacing is allowed here. Start up sub-modules.
	 */
	private void startUp(){
		//debuggingManager.getLuaManager().loadFile(ResourceManager.getFileDir("/scripts/source/Test.lua"));
		//debuggingManager.getStreamManager()
		
		coreBus.publish(StartUpEvent.class);
	}
	
	/**
	 * The main game loop
	 */
	private void gameLoop(){
		TickCalculator.getInstance().setFrequency(frequency);
		
		//Variables
		long waitPeriodTime = TickCalculator.getInstance().getStaticSleepTimeMs(); 	//The game's period in millisecond
		long previousTime = 0;	//The time the last frame ended
		long waitTime = 0;	//Actual time to wait
		long overtime = 0;	//Time that the cycle ran over/under
		
		running = true;	//Start the engine
		previousTime = System.currentTimeMillis();	//Get the current time in ms from the CPU

		
		//Main game loop
		while(running){
			coreBus.publish(GameLoopEvent.class);	//Publish the ticking event
			
			//Calculate sleep time stuff
			waitTime = waitPeriodTime - (System.currentTimeMillis() - previousTime) + overtime; //The time to sleep equals the FPS wait time minus the time the last frame took plus the overtime
			previousTime = System.currentTimeMillis();	//Update the next frame's previous time to the current time
			
			//If there is time left in the frame, sleep
			if(waitTime > 0){
				//Sleep
				try {
					Thread.sleep(waitTime);
					this.streamManager.println(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
					running = false;	//End the engine if an exception is thrown
				}
			} else{	//Otherwise, calculate overtime and don't sleep (hurry to catch up) until the engine has caught up
				overtime = waitTime; //Overtime is set to the remaining time
				while((-1 * overtime) >= waitPeriodTime){
					coreBus.publish(GameLoopEvent.class);	//Publish the ticking event
					overtime =+ waitPeriodTime;	//Add the standard tick time to overtime
				}
				coreBus.publish(GameLoopEvent.class);
				try {
					Thread.sleep(TickCalculator.getInstance().getSleepTimer());
					this.streamManager.println(TickCalculator.getInstance().getSleepTimer());
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
		coreBus.publish(ShutDownEvent.class);
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
		new Thread(FlamingOctoIronman.getInstance()).start();;	//Create a new instance of the game in a new thread and start it
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
	public static void stopGame(DeathReason forDying){
		reason = forDying;
		running = false;
	}
	
	//Get/set methods
		
	public JFrame getWindow(){
		return window;
	}
	
	public DeathReason getDeathReason(){
		return reason;
	}
	
	protected CoreEventBusService getCoreEventBusService(){
		return coreBus;
	}
	
	public CoreManagerManager getCoreManagerManager(){
		return coreManagerManager;
	}
	
	public StreamManager getStreamManager(){
		return streamManager;
	}
}
