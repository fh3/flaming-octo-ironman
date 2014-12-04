package com.flamingOctoIronman;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import com.flamingOctoIronman.core.event.CoreEventBusService;
import com.flamingOctoIronman.core.event.GameLoopEvent;
import com.flamingOctoIronman.core.event.InitializationEvent;
import com.flamingOctoIronman.core.event.PostInitializationEvent;
import com.flamingOctoIronman.core.event.ShutDownEvent;
import com.flamingOctoIronman.core.event.StartUpEvent;
import com.flamingOctoIronman.core.manager.CoreManager;
import com.flamingOctoIronman.core.manager.CoreManagerManager;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;
import com.flamingOctoIronman.subsystem.timer.TickCalculator;
import com.flamingOctoIronman.visualTest.MyWindow;

/**
 * The game engine's main class. Contains the main loop and is what is first created when the game is ran.
 * To start, create a new <code>Thread</code> from a new instance of this class and start it.
 */
public class FlamingOctoIronman implements Runnable{
	//Variables
	
	//Instance/most important variables
	/**
	 * The sole instance of the game
	 */
	private static FlamingOctoIronman instance;
	/**
	 * Whether or not the game is currently running
	 */
	private boolean running = false;
	
	//Timing stuff
	/**
	 * The frequency in Hertz that the game will be running at
	 */
	private static float frequency = 60;
	
	//Core stuff
	/**
	 * The main {@link CoreEventBusService} that handles all the {@link CoreEvent}s
	 */
	private CoreEventBusService coreBus;
	/**
	 * The main {@link CoreManagerManager} that handles all the {@link CoreManager}s and sub-managers
	 */
	private CoreManagerManager coreManagerManager;

	/**
	 * Handles all the output logging for the game.
	 * This is the one manager that should be in this class. It's here to allow for stream output before the whole 
	 * engine starts up
	 * The preferred method of referencing this is through the DebuggingManager once it's been created
	 */
	private StreamManager streamManager;
	
	/**
	 * The {@link JFrame} window, is only used for testing and will be removed later
	 */
	private MyWindow window;
	
	//Death
	/**
	 * The reason the game died
	 */
	private static DeathReason reason = null;
	
	/**
	 * Private, as there should only ever be one instance of this class.
	 */
	private FlamingOctoIronman(){
		
	}
	
	/**
	 * Starts and ends the game's life
	 * @return A result enum based on how the game exited
	 */
	private DeathReason startGame(){
		preinit();
		init();
		postinit();
		startUp();
		gameLoop();
		shutDown();
		exit();
		return reason;
	}
	
	/**
	 * The pre-initialization stage of the game's life. 
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>The stream manager is created</li>
	 * <li>The main log file is added to the stream manager</li>
	 * <li>The core bus is created</li>
	 * <li>The core manager manager is created</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * This method does not fire any events.
	 * <h2>What this method is used for</h2>
	 * This method is used to initialize and get a copy of the {@link StreamManager}, {@link CoreEventBusService}, and
	 * the {@link CoreManagerManager}.
	 * No managers other than the stream manager (with the exception of static methods) may be used here.
	 * No cross-module communication is allowed here.
	 * 
	 * @see com.flamingOctoIronman.subsystem.debugging.StreamManager
	 * @see com.flamingOctoIronman.core.event.CoreEventBusService
	 * @see com.flamingOctoIronman.core.manager.flaminoctoironman.CoreManagerManager
	 */
	private void preinit(){
		streamManager = new StreamManager();	//Create a new StreamManager object
		streamManager.addStreamToOutput(ResourceManager.getPrintStream("logs/log_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".txt"));	//I can access ResourceManager here because the method is static, which doesn't require the manager to be initialized for me to use it
		
		streamManager.println("Starting the game");
		
		coreBus = CoreEventBusService.getInstance();	//Gets the copy of the CoreEventBusService
		coreManagerManager = CoreManagerManager.getInstance(coreBus);	//Gets the copy of the CoreManagerManager
	}
	
	/**
	 * Initialization of the game. In this method, the core event bus and managers are initialized, and some 
	 * additional setup may be done here. No cross-module interfacing is allowed, except for static methods.
	 */
	/**
	 * The initialization stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>{@link CoreManagerManager#initialize(CoreEventBusService)} is called</li>
	 * <li>A new window is created by calling {@link MyWindow#MyWindow()}</li> (temporary)
	 * <li>An event is published</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * {@link InitializationEvent}
	 * 
	 * <h2>What this method is used for</h2>
	 * This method is used to create all the managers, and register their methods with the <code>CoreEventBusService</code>.
	 * No cross-module communication is allowed here.
	 * 
	 * @see com.flamingOctoIronman.core.manager.flaminoctoironman.CoreManagerManager
	 */
	private void init(){
		coreManagerManager.initialize();	
		//window = new MyWindow();
						
		//Public the event for this method
		coreBus.publish(InitializationEvent.class);
	}
	
	/**
	 * The post-initialization stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>An event is published</li>
	 * <li>Sub-module's methods are registered with the CoreEventBus via {@link CoreManagerManager#postInitialize(CoreEventBusService coreBus)}</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * <p>{@link PostInitializationEvent}</p>
	 * <h2>What this method is used for</h2>
	 * This method is used for the final initialization of the managers and sub-managers. Cross-module interfacing is allowed here.
	 */
	private void postinit(){
		coreManagerManager.postInitialize();
		coreBus.publish(PostInitializationEvent.class);
	}
	
	/**
	 * The start-up stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>An event is published</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * {@link StartUpEvent}
	 * <h2>What this method is used for</h2>
	 * This method is used to start any systems required by the game's overlaying framework. Game mechanics should be
	 * implemented here. Cross-module communication is allowed here.
	 */
	private void startUp(){		
		coreBus.publish(StartUpEvent.class);
	}
	
	/**
	 * The game loop stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>The main game loop is ran</li>
	 * <li>An event is published</li>
	 * <li>Any remaining time left in the tick is slept through to ensure a constant framerate</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * {@link GameLoopEvent} - Published every tick
	 * <h2>What this method is used for</h2>
	 * This method is used to fire an event on every tick in the game's life. Anything that needs to be dynamically updated
	 * should be annotated for this event. Cross-module communication is allowed here.
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
					Thread.sleep(waitTime);	//Try and sleep
				} catch (InterruptedException e) {
					e.printStackTrace();	//Print the stack trace for the fatal exception
					this.stopGame(DeathReason.EXCEPTION);	//End the engine if an exception is thrown
				}
			} else{	//Otherwise, calculate overtime and don't sleep (hurry to catch up) until the engine has caught up
				overtime = waitTime; //Overtime is set to the remaining time
				while((-1 * overtime) >= waitPeriodTime){
					coreBus.publish(GameLoopEvent.class);	//Publish the ticking event
					overtime =+ waitPeriodTime;	//Add the standard tick time to overtime
				}
				coreBus.publish(GameLoopEvent.class);	//Publish the ticking event
				try {
					Thread.sleep(TickCalculator.getInstance().getSleepTimer());	//Try and sleep
				} catch (InterruptedException e) {
					e.printStackTrace();	//Print the stack trace for the fatal exception
					this.stopGame(DeathReason.EXCEPTION);	//End the engine if an exception is thrown
				}
			}			
		}
	}
	/**
	 * The shut-down stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>An event is published</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * {@link ShutDownEvent}
	 * <h2>What this method is used for</h2>
	 * This method is used for notifying the managers and sub-systems that the game is being shut down.
	 * Cross module communication is allowed here, but may fail due to systems shutting down.
	 */
	private void shutDown(){
		coreBus.publish(ShutDownEvent.class);	//Publish the event
	}
	
	/**
	 * The exit stage of the game's life.
	 * <h2>In this stage:</h2>
	 * <ul>
	 * <li>The game actually ends</li>
	 * </ul>
	 * <h2>Events fired:</h2>
	 * This method does not fire any events.
	 * <h2>What this method is used for</h2>
	 * Any core finalization that needs to run right before the game shuts down.
	 */
	private void exit(){
		//Nothing to see here, move along.
	}
	
	/**
	 * Returns the game's instance. Creates and returns the instance if it is null.
	 * 
	 * @return The instance of the game.
	 */
	public static FlamingOctoIronman getInstance(){
		if(instance == null){	//If the instance hasn't been initialized,
			instance = new FlamingOctoIronman();	//Create a new instance
		}
		return instance;	//Return the instance
	}
	
	/**
	 * Starts the game and the game's thread.
	 * 
	 * @param args Arguments passed from the command line
	 * @see java.lang.Thread
	 */
	public static void main(String args[]){
		new Thread(FlamingOctoIronman.getInstance()).start();	//Create a new instance of the game in a new thread and start it
	}
	
	/**
	 * Starts the thread and calls {@link #startGame()}
	 */
	@Override
	public void run() {
		startGame();
	}
	
	/**
	 * Shuts the game down peacefully
	 */
	public void stopGame(DeathReason forDying){
		reason = forDying;
		running = false;
	}
	
	//Get/set methods
		
	/**
	 * Return the game's window
	 * @return	The game's window
	 */
	public JFrame getWindow(){
		return window;
	}
	
	/**
	 * Returns the reason why the game died	
	 * @return	Why the game died
	 */
	public DeathReason getDeathReason(){
		return reason;
	}
	
	/**
	 * Returns the {@link CoreEventBusService} instance for the game
	 * @return The <code>CoreEventBusService</code> for the game
	 */
	protected CoreEventBusService getCoreEventBusService(){
		return coreBus;
	}
	
	/**
	 * Returns the {@link CoreManagerManager} instance for the game
	 * @return	The <code>CoreManagerManager</code> for the game
	 */
	public CoreManagerManager getCoreManagerManager(){
		return coreManagerManager;
	}
	
	/**
	 * Returns the game's {@link StreamManager}. The preferred way of accessing this is through the {@link DebugginManager} after it has been
	 * initialized.
	 * @return The game's <code>StreamManager</code>
	 */
	public StreamManager getStreamManager(){
		return streamManager;
	}
	
	/**
	 * Get a {@link CoreManager} based on the passed <code>String</code>. This method is mainly for convenience, if called before
	 * {@link FlamingOctoIronman#init()} is called.
	 * 
	 * @param simpleName	The simple name of the class you want to get the instance of.
	 * @return	The CoreManager associated with the simpleName.
	 */
	public static CoreManager getCoreManager(String simpleName){
		return FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(simpleName);
	}
}
