package com.flamingOctoIronman.HID.inputEvents;

import java.util.ServiceLoader;

import com.flamingOctoIronman.framework.event.EventBusService;

public class InputEventBusService extends EventBusService<InputEvent>{

	public InputEventBusService() {
		super(ServiceLoader.load(InputEvent.class));
	}

	@Override
	public Class getHandlerAnnotation() {
		return InputHandler.class;
	}

}
