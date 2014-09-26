package com.flamingOctoIronman.events;

import java.lang.reflect.Method;

/**
 * This class is an abstract implementation of <code>Event</code>. It provides all methods required by <code>Event</code>
 * except for <code>compareClass()</code>.
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
