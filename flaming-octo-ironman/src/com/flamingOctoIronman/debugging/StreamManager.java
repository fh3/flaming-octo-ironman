package com.flamingOctoIronman.debugging;

import java.io.PrintStream;
import java.util.ArrayList;

public class StreamManager {
	private ArrayList<PrintStream> outStream;
	private Verbosity level = Verbosity.HIGH;
	private ErrorStreamIntercepter errorIntercept;
	
	public StreamManager(){
		outStream = new ArrayList<>();
		outStream.add(System.out);
		errorIntercept = new ErrorStreamIntercepter();
		//System.setErr(errorIntercept);
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
