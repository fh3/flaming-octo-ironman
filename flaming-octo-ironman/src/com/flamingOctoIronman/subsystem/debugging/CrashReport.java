package com.flamingOctoIronman.subsystem.debugging;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;
import com.flamingOctoIronman.subsystem.timer.Timer;

public class CrashReport {
	private FileWriter writer;
	
	public CrashReport(DeathReason deathReason) {
		writer = ResourceManager.getFileWriter(ResourceManager.getFileDir("logs/crashlog_" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + ".txt"));
		
		//Output for file
		try {
			this.println("FlamingOctoIronman has crashed!");
			this.println("");
			this.println("Reason for death: " + deathReason);
			this.println("Crash occurred at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			this.println("Ticks passed since start: " + Timer.getTickCount());
			this.println("Max JVM memory: " + Runtime.getRuntime().maxMemory());
			this.println("Free memory: " + Runtime.getRuntime().freeMemory());
			this.println("Used memory: " + (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()));
			this.println("");
			this.println("Stack traces: ");
			Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
			for(Thread key : traces.keySet()){
				this.println("Thread: " + key.getName());
				for(StackTraceElement element : traces.get(key)){
					println("Class " + element.getClassName() + " Line " + element.getLineNumber() + ": " + element.getMethodName());
				}
				this.println("");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void println(String s) throws IOException{
		writer.write(s + "\n");
	}

}
