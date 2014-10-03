package com.flamingOctoIronman.debugging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamInterceptor extends PrintStream {

	private PrintStreamInterceptor(File file) throws FileNotFoundException {
		super(file);
	}
	
	/**
	 * 
	 */
	@Override
	public void println(String s){
		DebuggingManager.getInstance().println(s);
	}

}