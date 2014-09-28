package com.flamingOctoIronman.events.coreEvents;

import java.lang.reflect.Method;

import com.flamingOctoIronman.events.Event;

/**
 * This class is an abstract implementation of <code>Event</code>. It provides all methods required by <code>Event</code>
 * except for <code>getName()</code>.
 * @author Quint
 *
 */
public abstract class CoreEvent extends Event{

	public CoreEvent() {
		super(CoreEventHandler.class);
	}

	@Override
	public boolean compareNames(Method m) {
		return m.getAnnotation(CoreEventHandler.class).event().equals(getName());
	}
	
}
