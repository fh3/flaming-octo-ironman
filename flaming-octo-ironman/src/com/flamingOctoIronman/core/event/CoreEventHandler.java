package com.flamingOctoIronman.core.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is to mark methods with the <code>CoreEvent</code> they subscribe to.
 * @author Quint
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoreEventHandler {
	/**
	 * 
	 * @return The <code>getSimplifiedName()</code> of the event being subscribed to
	 */
	public String event();
}
