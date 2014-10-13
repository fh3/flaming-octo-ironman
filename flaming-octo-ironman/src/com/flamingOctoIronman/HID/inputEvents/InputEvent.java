package com.flamingOctoIronman.HID.inputEvents;

import java.lang.reflect.Method;

import com.flamingOctoIronman.core.event.CoreEventHandler;
import com.flamingOctoIronman.eventFramework.Event;

public abstract class InputEvent extends Event {

	public InputEvent() {
		super(InputHandler.class);
	}
	
	@Override
	public boolean compareNames(Method m) {
		return m.getAnnotation(InputHandler.class).event().equals(getName());
	}

}
