package com.flamingOctoIronman.subsystem.HID.inputEvents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is to mark methods with the <code>InputEvent</code> they subscribe to.
 * @author Quint
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputHandler {
	/**
	 * 
	 * @return The <code>getSimplifiedName()</code> of the event being subscribed to
	 */
	String event();

}
