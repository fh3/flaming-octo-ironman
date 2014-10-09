package com.flamingOctoIronman.debugging;

import com.flamingOctoIronman.CrashReport;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.Manager;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

public class DebuggingManager extends Manager{
	private StreamManager streams;
	
	private static DebuggingManager instance;
	
	
	private DebuggingManager(){
		streams = new StreamManager();
	}
	
	public static DebuggingManager getInstance(){
		if(instance == null){
			instance = new DebuggingManager();
		}
		
		return instance;
	}
	
	public StreamManager getStreamManager(){
		return streams;
	}
	
	@CoreEventHandler(event = "ShutDownEvent")
	public void shutDown(){
		new CrashReport(FlamingOctoIronman.getInstance().getDeathReason());
	}
}