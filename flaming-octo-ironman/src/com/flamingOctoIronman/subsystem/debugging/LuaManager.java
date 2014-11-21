package com.flamingOctoIronman.subsystem.debugging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.luaj.vm2.LuaError;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.framework.event.EventHandler;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

//TODO I might need to make this a separate thread
public class LuaManager {
	private StreamManager out;
	
	private ScriptEngineManager sem = new ScriptEngineManager();
	private ScriptEngine e = sem.getEngineByName("luaj");
	private ScriptEngineFactory f = e.getFactory();
	
	private Scanner streamScanner = null;
		
	public LuaManager(){
		out = ((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(DebuggingManager.class.getSimpleName())).getStreamManager();
		
		out.println("***************Lua Engine***************");
		out.println( "Engine name: " +f.getEngineName() );
        out.println( "Engine Version: " +f.getEngineVersion() );
        out.println( "Language Name: " +f.getLanguageName() );
        out.println( "Language Version: " +f.getLanguageVersion() );
        out.println("****************************************");
	}
	
	public void registerObjects(String[] luaName, Object[] javaObject){
		for(int i = 0; i < luaName.length; i++){
			this.registerObject(luaName[i], javaObject[i]);
		}
	}
	
	@EventHandler(event = "StartUpEvent")
	public void startUp(){
		this.evaluate("print 'Loading Scripts'");
		this.loadFile(ResourceManager.getFileDir("/scripts/source/Test.lua"));
		this.evaluate("main()");
	}
	
	public void registerObject(String luaName, Object javaObject){
		e.put(luaName, javaObject);
	}
	
	//TODO might change the way this works later
	@EventHandler(event = "GameLoopEvent")
	public void evaluate(){
		if(streamScanner != null){
			try {
				e.eval(streamScanner.next());
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void evaluate(String toEvaluate){
		try {
			e.eval(toEvaluate);
		} catch (ScriptException e) {
			((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(DebuggingManager.class.getSimpleName())).getStreamManager().printCaughtException(e);
		} catch(LuaError e){
			((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(DebuggingManager.class.getSimpleName())).getStreamManager().printCaughtException(e);
		}
	}
	
	public void addObject(String luaName, Object toAccess){
		e.put(luaName, toAccess);
	}
	
	public void addObjects(String[] luaNames, Object[] toAccess){
		for(int i = 0; i < luaNames.length; i++){
			this.addObject(luaNames[i], toAccess[i]);
		}
	}
	
	public void loadFile(File toLoad){
		try {
			e.eval(new FileReader(toLoad));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInputStream(InputStream toRead){
		streamScanner = new Scanner(toRead);
	}
}