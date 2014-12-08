package com.flamingOctoIronman.subsystem.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.manager.CoreManager;
import com.flamingOctoIronman.framework.event.EventHandler;

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
	
	@EventHandler(event = "InitializationEvent")
	public void Initialization(){
		streams = FlamingOctoIronman.getInstance().getStreamManager();
		luaManager = new LuaManager();
	}
	
	@EventHandler(event = "GameLoopEvent")
	public void tick(){
		luaManager.evaluate();
	}
	
	@EventHandler(event = "ShutDownEvent")
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
