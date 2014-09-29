package com.flamingOctoIronman.HID.inputEvents;

import java.lang.reflect.Method;

/**
 * A simple class for holding the pressed and released methods associated with a key press
 * @author Quint Heinecke
 *
 */
public class KeyMethods {
	private Method pressed;
	private Method released;
	
	public KeyMethods(Method pressed, Method released){
		this.pressed = pressed;
		this.released = released;
	}
	
	/**
	 * 
	 * @return the method to be called when pressed
	 */
	public Method getPressed(){
		return pressed;
	}
	
	/**
	 * 
	 * @return the method to be called when released
	 */
	public Method getReleased(){
		return released;
	}
}
