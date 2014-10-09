package com.flamingOctoIronman;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.flamingOctoIronman.timer.Timer;

public class CrashReport {
	private FileWriter writer;
	
	public CrashReport(DeathReason deathReason) {
		// TODO Auto-generated constructor stub
		//TODO Call resource manager to get a new file printstream
		FileWriter writer = null;
		
		//Output for file
		try {
			this.println("FlamingOctoIronman has crashed!");
			this.println("");
			this.println("Reason for death: " + deathReason);
			this.println("Crash occoured at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			this.println("Ticks passed since start: " + Timer.getTickCount());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void println(String s) throws IOException{
		writer.write(s + "\n");
	}

}
