/**
 * 
 */
package com.flamingOctoIronman.HID;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import com.flamingOctoIronman.HID.inputEvents.InputEventBusService;
import com.flamingOctoIronman.HID.inputEvents.InputMapper;
import com.flamingOctoIronman.HID.inputEvents.KeyTypedEvent;
import com.flamingOctoIronman.events.coreEvents.CoreEventHandler;

/**
 * This class manages all input and output systems for the game.
 * @author Quint Heinecke
 *
 */
//TODO Note to self: may need to rework this due to AWT threading model.
public class HIDManager implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener{
	private InputEventBusService inputEventBus;	//Hold and load all of the input events
	private InputMapper keyMap;	//Map the keys to a function
	private MouseEvent mouseEvent; //The last mouse event that occurred
	private boolean mouseEventThisTick; //True if there was an event on this tick
	
	/**
	 * Constructs a new instance of HIDManager
	 */
	public HIDManager(){
		inputEventBus = new InputEventBusService();
		keyMap = new InputMapper();
		mouseEventThisTick = false;
	}
	
	/**
	 * Handle stuff for each tick
	 */
	@CoreEventHandler(event = "GameLoopEvent")
	public void gameTick(){
		mouseEventThisTick = false;
	}
	
	/**
	 * Registers the <code>JFrame frame</code> for key listening
	 * @param frame The frame to register
	 */
	public void registerFrame(JFrame frame){
		//Add keyboard listeners to the frame
		frame.addKeyListener(this);
		frame.setFocusTraversalKeysEnabled(false);
		
		//Add mouse listeners to the frame
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
	}
	
	/**
	 * Subscribes an object to input events
	 * @param subscriber The object to subscribe
	 */
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
	
	/**
	 * Checks to see if the key associated with the given <code>String</code> is pressed
	 * @param UID The <code>String</code> to check
	 * @return True if the key is pressed, false otherwise
	 */
	public boolean isPressed(String UID){
		return this.keyMap.isPressed(UID);
	}
	
	/**
	 * Adds a <code>String</code>/<code>Key</code> pair to the key map
	 * @param UID
	 * @param Key
	 */
	public void putKeyMap(String UID, int Key){
		this.keyMap.putKeyMap(UID, Key);
	}
	
	/**
	 * 
	 * @return The last <code>MouseEvent</code> to occur
	 */
	public MouseEvent getMouseEvent(){
		return mouseEvent;
	}
	
	/**
	 * 
	 * @return If there was a <code>MouseEvent</code> on this tick
	 */
	public boolean mouseEventThisTick(){
		return mouseEventThisTick;
	}

	//TODO this needs testing, may flop
	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}

	/**
	 * Handles mouse events.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		mouseEvent = e;
		mouseEventThisTick = true;
	}
}
