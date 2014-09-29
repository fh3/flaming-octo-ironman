package com.flamingOctoIronman.HID.inputEvents;

import java.awt.event.KeyEvent;

/**
 * Hold information that distinguishes key presses from each other based on key pressed and modifers.
 * @author Quint Heinecke
 *
 */
public class KeyInput {
	private int extendedKeyCode;
	private int extendedModifiers;
	
	/**
	 * Creates a new <code>KeyInput</code> based off a <code>KeyEvent</code>
	 * @param e the <code>KeyEvent</code> created when a key is pressed.
	 */
	public KeyInput(KeyEvent e){
		extendedKeyCode = e.getExtendedKeyCode();
		extendedModifiers = e.getModifiersEx();
	}

	/**
	 * 
	 * @return the modifier keys associated with the key press
	 */
	public int getExtendedModifiers() {
		return extendedModifiers;
	}
	
	/**
	 * 
	 * @return the key associated with the key press
	 */
	public int getExtendedKeyCode() {
		return extendedKeyCode;
	}
	
	public boolean compareToKeyEvent(KeyEvent e){
		if(e.getExtendedKeyCode() == extendedKeyCode && e.getModifiersEx() == extendedModifiers){
			return true;
		}
		return false;
	}
}
