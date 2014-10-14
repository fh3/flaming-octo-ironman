package com.flamingOctoIronman.subsystem.HID.inputEvents;

import java.lang.reflect.Method;

import com.flamingOctoIronman.core.event.CoreEventHandler;
import com.flamingOctoIronman.framework.event.Event;

public abstract class InputEvent extends Event {

	public InputEvent() {
		super();
	}
	
	@Override
	public boolean compareNames(Method m) {
		return m.getAnnotation(InputHandler.class).event().equals(getName());
	}

}
