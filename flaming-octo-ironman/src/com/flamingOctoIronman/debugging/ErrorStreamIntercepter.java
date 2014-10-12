package com.flamingOctoIronman.debugging;

import com.flamingOctoIronman.FlamingOctoIronman;

public class ErrorStreamIntercepter extends PrintStreamIntercepter {
	@Override
	public void println(String s){
		FlamingOctoIronman.getInstance().getStreamManager().printError(s);
	}
}
