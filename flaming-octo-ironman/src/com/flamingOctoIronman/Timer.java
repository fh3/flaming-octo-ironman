/**
 * 
 */
package com.flamingOctoIronman;

import com.flamingOctoIronman.events.CoreEventHandler;

/**
 * @author Quint
 *
 */
public class Timer {
	private static int tickCount = 0;
	
	@CoreEventHandler(event = "GameLoopEvent")
	public static void tickEvent(){
		tickCount++;
		System.out.println(tickCount);
	}
	
	public static int getTickCount(){
		return tickCount;
	}
}
