/**
 * 
 */
package com.flamingOctoIronman.HID;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.flamingOctoIronman.HID.inputEvents.InputEventBusService;
import com.flamingOctoIronman.HID.inputEvents.InputMapper;
import com.flamingOctoIronman.HID.inputEvents.KeyTypedEvent;

/**
 * This class manages all input and output systems for the game.
 * @author Quint Heinecke
 *
 */
public class HIDManager implements KeyListener{
	private InputEventBusService inputEventBus;
	private InputMapper keyMap;
	
	public HIDManager(){
		inputEventBus = new InputEventBusService();
		keyMap = new InputMapper();
	}
	
	public void registerFrame(JFrame frame){
		frame.addKeyListener(this);
		frame.setFocusTraversalKeysEnabled(false);
	}
	
	public void subscribeInput(Object subscriber){
		inputEventBus.subscribe(subscriber);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		inputEventBus.publish(KeyTypedEvent.class);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.keyMap.putPressMap(e.getExtendedKeyCode(), true);		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.keyMap.putPressMap(e.getExtendedKeyCode(), false);
	}
	
	public boolean isPressed(String UID){
		return this.keyMap.isPressed(UID);
	}
}
