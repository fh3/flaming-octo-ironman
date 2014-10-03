package com.flamingOctoIronman.debugging;

import java.io.PrintStream;
import java.util.ArrayList;

public class DebuggingManager {
	private ArrayList<PrintStream> outStream;
	private static DebuggingManager instance;
	private PrintStreamInterceptor outCapture;
	private String name;
	
	private DebuggingManager(){
		System.setOut(outCapture);
		outStream = new ArrayList<>();
	}
	
	public static DebuggingManager getInstance(){
		if(instance == null){
			instance = new DebuggingManager();
		}
		
		return instance;
	}
	
	public void addStreamToOutput(PrintStream toAdd){
		outStream.add(toAdd);
	}
	
	public void println(String s){
		for(PrintStream stream : outStream){
			stream.println();
		}
	}
}
