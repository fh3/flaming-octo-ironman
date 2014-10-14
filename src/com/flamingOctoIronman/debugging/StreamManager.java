package com.flamingOctoIronman.debugging;

import java.io.PrintStream;
import java.util.ArrayList;

public class StreamManager {
	private ArrayList<PrintStream> outStream;
	private Verbosity level = Verbosity.MEDIUM;
	private ErrorStreamIntercepter errorIntercept;
	
	public StreamManager(){
		outStream = new ArrayList<>();
		outStream.add(System.out);
		errorIntercept = new ErrorStreamIntercepter();
		System.setErr(errorIntercept);
	}
	
	public void addStreamToOutput(PrintStream toAdd){
		outStream.add(toAdd);
	}
	
	public void println(String s){
		if(level == Verbosity.HIGH || level == Verbosity.MEDIUM){
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
	
	public Verbosity getVerbosity(){
		return level;
	}

	public void println(long l) {
		for(PrintStream stream : outStream){
			stream.println(l);
		}
	}
	
	public void printCaughtException(Exception e){
		this.println("A " + e.getClass().getSimpleName() + "was caught.");
		this.println("Message: " + e.getMessage());
		this.println("Cause: " + e.getCause());
		this.println("Stack trace: " + e.getStackTrace().toString());
	}
}
