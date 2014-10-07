package com.flamingOctoIronman.debugging;

import java.io.PrintStream;
import java.util.ArrayList;

import com.flamingOctoIronman.Manager;

public class DebuggingManager extends Manager{
	private ArrayList<PrintStream> outStream;
	private static DebuggingManager instance;
	private Verbosity level = Verbosity.HIGH;
	private ErrorStreamIntercepter errorIntercept;
	
	private DebuggingManager(){
		outStream = new ArrayList<>();
		errorIntercept = new ErrorStreamIntercepter();
		System.setErr(errorIntercept);
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
		if(level == Verbosity.HIGH){
			for(PrintStream stream : outStream){
				stream.println(s);
			}
		}
	}
	
	public void printError(String s){
		for(PrintStream stream : outStream){
			stream.println(s);
		}
	}

	public void println(int i) {
		for(PrintStream stream : outStream){
			stream.println(i);
		}
	}
	
	public void setVerbosity(Verbosity v){
		level = v;
	}
}
