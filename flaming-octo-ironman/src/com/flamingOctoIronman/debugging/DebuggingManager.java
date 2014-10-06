package com.flamingOctoIronman.debugging;

import java.io.PrintStream;
import java.util.ArrayList;

import com.flamingOctoIronman.Manager;

public class DebuggingManager extends Manager{
	private ArrayList<PrintStream> outStream;
	private static DebuggingManager instance;
	private PrintStreamInterceptor outCapture;
	
	private DebuggingManager(){
		System.setOut(outCapture);
		outStream = new ArrayList<>();
		outStream.add(outCapture);
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
			stream.println(s);
		}
	}
}
