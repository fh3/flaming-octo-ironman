package com.flamingOctoIronman.eventFramework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This is a dummy class used as an example on how to make annotations for events.
 * @author Quint Heinecke
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DummyAnnotation {
	/**
	 * @return The <code>getSimplifiedName()</code> of the event being subscribed to
	 */
	public String event();
}
