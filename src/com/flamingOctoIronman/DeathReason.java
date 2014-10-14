package com.flamingOctoIronman;

/**
 * The types in this enum are for showing why the engine died.
 * @author Quint
 *
 */
public enum DeathReason {
	/**
	 * This is for a normal death reason (ex. game window closed)
	 */
	NORMAL,
	/**
	 * This is for when an exception is thrown
	 */
	EXCEPTION,
	/**
	 * This is for when an error is thrown
	 */
	ERROR,
	/**
	 * This is for an unknown death reason
	 */
	OTHER;
}
