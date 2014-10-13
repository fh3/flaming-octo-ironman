package com.flamingOctoIronman.managerFramework;

public abstract class Manager {
	/**
	 * Returns the simple name of the class.
	 * @return The simple name of the class
	 */
	public abstract String getName();
	
	/**
	 * This is a list of all sub-managers in the manager's class.
	 * @return An array of all sub-managers for the manager, or null if the manager has non sub-managers
	 */
	public Object[] getSubManagers(){
		return null;
	}
}
