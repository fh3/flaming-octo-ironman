package com.flamingOctoIronman.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;

public class OutStreamIntercepter extends PrintStreamIntercepter {
	
	@Override
	public void println(String s){
		FlamingOctoIronman.getInstance().getDebuggingManager().getStreamManager().println(s);
	}
}
