/**
 * 
 */
package com.flamingOctoIronman.subsystem.timer;

import org.lwjgl.opengl.Display;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.manager.CoreManager;
import com.flamingOctoIronman.framework.event.EventHandler;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;
/**
 * @author Quint
 *
 */
public class Timer extends CoreManager{
	private static StreamManager out = FlamingOctoIronman.getInstance().getStreamManager();

	private static long lastFrame;
	private static int deltaT;
	private static int frameCount;
	private static long lastFPS = getTime();
	private static long fps;
	private static long totalFrames;
	
	public static long getTime(){
		return System.nanoTime() / 1000000;	//Time in ms
	}
	
	private static int calculateDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
	
	private static void updateFPS() {
		if(getTime() - lastFPS < 1000){
			frameCount++;
		} else{
			fps = frameCount;
			frameCount = 0;
			lastFPS += 1000;
		}
		totalFrames++;
	}
	
	public static int getDelta(){
		return deltaT;
	}
	
	public static float getFPS(){
		return fps;
	}
	
	@EventHandler(event = "GameLoopEvent")
	public static void updateTimer(){
		deltaT = calculateDelta();
		updateFPS();
	}

	@Override
	public String getName() {
		return Timer.class.getSimpleName();
	}

	public static long getTickCount() {
		// TODO Auto-generated method stub
		return totalFrames;
	}
}
