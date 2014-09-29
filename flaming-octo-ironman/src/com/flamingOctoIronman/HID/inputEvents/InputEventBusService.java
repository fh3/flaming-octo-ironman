package com.flamingOctoIronman.HID.inputEvents;

import java.util.ServiceLoader;

import com.flamingOctoIronman.eventFramework.EventBusService;

public class InputEventBusService extends EventBusService{

	public InputEventBusService() {
		super(ServiceLoader.load(InputEvent.class));
	}

}
