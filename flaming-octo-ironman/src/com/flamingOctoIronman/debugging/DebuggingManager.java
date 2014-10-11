package com.flamingOctoIronman.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.Manager;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

public class DebuggingManager extends Manager{
	private StreamManager streams;
	private static DebuggingManager instance;
	private LuaManager luaManager;

	private DebuggingManager(){
		
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
	
	@CoreEventHandler(event = "StartUpEvent")
	public void startUp(){
		streams = new StreamManager();
		luaManager = new LuaManager();
	}
	
	@CoreEventHandler(event = "GameLoopEvent")
	public void tick(){
		luaManager.evaluate();
	}
	
	@CoreEventHandler(event = "ShutDownEvent")
	public void shutDown(){
		new CrashReport(FlamingOctoIronman.getInstance().getDeathReason());
	}
	
	public LuaManager getLuaManager(){
		return this.luaManager;
	}
}
