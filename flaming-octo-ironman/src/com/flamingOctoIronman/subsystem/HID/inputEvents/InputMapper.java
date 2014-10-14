package com.flamingOctoIronman.subsystem.HID.inputEvents;

import java.util.Hashtable;

/**
 * This class handles the mapping of keys to methods
 * @author Quint Heinecke
 *
 */
public class InputMapper {
	private Hashtable<Integer, Boolean> pressMap;
	private Hashtable<String, Integer> keyMap;
	
	public InputMapper(){
		this.pressMap = new Hashtable<Integer, Boolean>();
		this.keyMap = new Hashtable<String, Integer>();
	}
	
	public InputMapper(Hashtable<Integer, Boolean> pressMap, Hashtable<String, Integer> keyMap){
		if(pressMap == null){
			this.pressMap = new Hashtable<Integer, Boolean>();
		} else{
			this.pressMap = pressMap;
		}
		
		if(keyMap == null){
			this.keyMap = keyMap;
		} else{
			this.keyMap = new Hashtable<String, Integer>();
		}
	}

	public void putPressMap(int pressedKey, boolean pressed){
		this.pressMap.put(pressedKey, pressed);
	}

	public boolean isPressed(int pressedKey){
		return this.pressMap.get(pressedKey);
	}
	
	public void putKeyMap(String UID, int key){
		this.keyMap.put(UID, key);
	}
	
	public boolean isPressed(String UID){
		return this.pressMap.get(keyMap.get(UID));
	}

}
