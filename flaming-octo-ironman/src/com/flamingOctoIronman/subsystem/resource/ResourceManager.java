package com.flamingOctoIronman.subsystem.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.event.CoreEventHandler;
import com.flamingOctoIronman.core.manager.CoreManager;

/**
 * This is the main class that loads and unloads files.
 * @author Quint
 *
 */

public class ResourceManager extends CoreManager{
	private static String classDir = System.getProperty("java.class.path").substring(0, System.getProperty("java.class.path").indexOf("bin"));
	public ResourceManager(){
		
	}
	
	/**
	 * This method runs whatever needs to be run at the initialization stage of the game's life.
	 */
	@CoreEventHandler(event = "InitializationEvent")
	public void initialization() {
		
	}
	@CoreEventHandler(event = "StartUpEvent")
	public void startUpEvent(){
		
	}
	
	public static Path getPathDir(String relativePath){
		return Paths.get(classDir, relativePath);
	}
	
	public static File getFileDir(String relativePath){
		return getPathDir(relativePath).toFile();
	}
	
	public static Path getPathFile(String relativePath, String filename){
		return getPathDir(relativePath);
	}
	
	public static PrintStream getPrintStream(File ofStream){
		try {
			return new PrintStream(ofStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static PrintStream getPrintStream(String locationOfStream){
		try {
			return new PrintStream(getFileDir(locationOfStream));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public static FileWriter getFileWriter(String location){
		try {
			return new FileWriter(location);
		} catch (IOException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
			return null;
		}
	}
	
	public static FileWriter getFileWriter(File location){
		try {
			return new FileWriter(location);
		} catch (IOException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
			return null;
		}
	}

}
