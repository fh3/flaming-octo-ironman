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
	private static float tickCount = 0;
	private float passedTicks;
	public Timer(){
		setCurrentTick();
	}
	
	/**
	 * Called on every tick, increase the tick count
	 */
	@CoreEventHandler(event = "GameLoopEvent")
	public static void tickEvent(){
		tickCount++;
		System.out.println(String.format("Tick: %.0f", tickCount));
	}
	
	/**
	 * Returns the current number of ticks that have passed since the start of the game
	 * @return Ticks passed
	 */
	public static float getTickCount(){
		return tickCount;
	}
	
	/**
	 * Returns the number of ticks that have passed since the base tick count was set
	 * @return Ticks passed
	 */
	public float ticksPassed(){
		return tickCount - passedTicks;
	}
	
	/**
	 * Sets the base tick count to the current tick
	 */
	public void setCurrentTick(){
		passedTicks = tickCount;
	}
	
	/**
	 * Sets the base number of ticks to the given tick number
	 * @param Tick to set the base tick count to
	 */
	public void setCurrentTick(float tick){
		passedTicks = tick;
	}
}
