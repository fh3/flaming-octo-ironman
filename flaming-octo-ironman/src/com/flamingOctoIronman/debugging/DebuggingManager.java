package com.flamingOctoIronman.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.CoreManager;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

public class DebuggingManager extends CoreManager{
	private StreamManager streams;
	private static DebuggingManager instance;
	private LuaManager luaManager;

	private DebuggingManager(boolean nil){
	}
	
	public DebuggingManager(){
		getInstance();
	}
	
	public static DebuggingManager getInstance(){
		if(instance == null){
			instance = new DebuggingManager(false);
		}
		
		return instance;
	}
	
	public StreamManager getStreamManager(){
		return streams;
	}
	
	@Override
	public Object[] getSubManagers(){
		Object[] list = {luaManager, streams};
		return list;
	}
	
	@CoreEventHandler(event = "PostInitializationEvent")
	public void postInitialization(){
		streams = FlamingOctoIronman.getInstance().getStreamManager();
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
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
