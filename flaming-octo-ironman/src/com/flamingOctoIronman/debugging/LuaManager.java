package com.flamingOctoIronman.debugging;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import com.flamingOctoIronman.FlamingOctoIronman;

//TODO I might need to make this a seperate thread
public class LuaManager {
	private Globals globals;
	
	public LuaManager(){
		globals = JsePlatform.standardGlobals();
		FlamingOctoIronman.getInstance().getDebuggingManager().getStreamManager().println(globals.running.toString());
	}
}
