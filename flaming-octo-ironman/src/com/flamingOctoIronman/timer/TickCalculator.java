package com.flamingOctoIronman.timer;

import java.util.LinkedList;
import java.util.Queue;

import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

public class TickCalculator {
	private static TickCalculator instance;
	private Timer timer;
	private float frequency;
	private Queue<Long> queue;
	private long lastTime = 0;
	private long timePassed;
	
	private TickCalculator(){
		timer = new Timer();
		queue = new LinkedList<Long>();
	}
	
	public static TickCalculator getInstance(){
		if(instance == null){
			instance = new TickCalculator();
		}
		return instance;
	}
	
	public long getSleepTimer(){
		return getStaticSleepTimeMs();
	}
	
	public void setFrequency(float hertz){
		frequency = hertz;
	}
	
	public float getPeriod(){
		return (1 / frequency);
	}
	
	public long getStaticSleepTimeMs(){
		return (long) (1000 * getPeriod());
	}

	@CoreEventHandler(event = "GameTickEvent")
	public void updateRealTimer(){
		timePassed = System.nanoTime() - lastTime;
		queue.remove();
		queue.add(timePassed);
		lastTime = System.nanoTime();
	}
	
	public long getPreviousTime(){
		return timePassed;
	}
	
	public long getAverageTime(){
		long sum = 0;
		for(long time : (Long[])queue.toArray()){
			sum =+ time;
		}
		return sum / queue.toArray().length;
	}
	
}
