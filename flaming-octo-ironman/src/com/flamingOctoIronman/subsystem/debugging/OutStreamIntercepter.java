package com.flamingOctoIronman.subsystem.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;

public class OutStreamIntercepter extends PrintStreamIntercepter {
	
	@Override
	public void println(String s){
		FlamingOctoIronman.getInstance().getStreamManager().println(s);
	}
}
