package com.flamingOctoIronman.HID.inputEvents;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.Hashtable;

/**
 * This class handles the mapping of keys to methods
 * @author Quint Heinecke
 *
 */
public class InputMapper {
	//This maps the KeyInput to the Method that should be called when the key is pressed
	private Hashtable<KeyInput, KeyMethods> keyMap;
	
	/**
	 * Create a new empty key map
	 */
	public InputMapper(){
		this.keyMap = new Hashtable<KeyInput, KeyMethods>();
	}
	
	/**
	 * Creates a new key map with the given map
	 * @param map the map pre-loaded with <code>KeyInput</code>s and <code>Methods</code>
	 */
	public InputMapper(Hashtable<KeyInput, KeyMethods> map){
		this.keyMap = map;
	}
	
	/**
	 * Adds a key/method set to the map
	 * @param pressedKey the pressed key
	 * @param pressed the method to call when pressed
	 * @param released the method to call when released
	 */
	public void putMap(KeyInput pressedKey, Method pressed, Method released){
		this.keyMap.put(pressedKey, new KeyMethods(pressed, released));
	}
	
	/**
	 * Retrieves a method from the map with the given key
	 * @param pressedKey the key pressed
	 * @return the KeyMethod object that stores the press and release methods
	 */
	public KeyMethods getMap(KeyInput pressedKey){
		return this.keyMap.get(pressedKey);
	}
	
	public KeyMethods getMap(KeyEvent e){
		return this.keyMap.get(new KeyInput(e));
	}
}
