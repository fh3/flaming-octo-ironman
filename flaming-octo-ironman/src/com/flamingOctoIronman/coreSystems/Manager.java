package com.flamingOctoIronman.coreSystems;

public interface Manager {
	//All methods implementing this interface need to register for these events via the EventBus
	public void initialize();
	public void startUp();
	public void shutDown();
}
