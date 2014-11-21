package com.flamingOctoIronman.core.event;

import java.lang.reflect.Method;

import com.flamingOctoIronman.framework.event.Event;

/**
 * This class is an abstract implementation of {@link Event}. It provides all methods required by <code>Event</code>
 * except for <code>getName()</code>.
 * @author Quint
 *
 */
public abstract class CoreEvent extends Event{
	/**
	 * Nothing to see here, move along.
	 */
	public CoreEvent() {
		super();
	}	
}
