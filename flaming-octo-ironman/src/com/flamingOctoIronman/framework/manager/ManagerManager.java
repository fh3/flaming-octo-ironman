package com.flamingOctoIronman.framework.manager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.flamingOctoIronman.framework.event.Event;
import com.flamingOctoIronman.framework.event.EventBusService;
import com.flamingOctoIronman.framework.event.EventHandler;

/**
 * This class handles all the managers. It loads them, then registers them for {@link Event}s.
 * @author Quint
 *
 * @param <T1>	The class that extends {@link Manager}
 * @param <T2>	The class that extends <code>Event</code>
 */
public abstract class ManagerManager<T1 extends Manager> {
	
	/**
	 * A reference to the {@link EventServiceBus} that controls all the <code>T2 Event</code>s
	 */
	private EventBusService busService;
	
	/**
	 * Loads all the <code>T1 Manager</code>s
	 */
	private ServiceLoader<T1> classLoader;
	/**
	 * The top level {@link Class} <code>T1</code> that extends <code>Event</code>
	 */
	private Class<T1> toLoad;
	
	/**
	 * A map that relates the simple name of each manager to the <code>Manager</code> instance
	 */
	private HashMap<String, T1> classMap;
	
	/**
	 * Creates a new <code>ManagerManager</code>
	 * @param busService	The <code>EventBusService</code> associated with the <code>Manager</code>
	 * @param toLoad	The top level <code>Class T1</code> to load with the <code>ServiceLoader</code>. Generics cannot
	 * be used here to to generic's limitations.
	 */
	public ManagerManager(EventBusService busService, Class<T1> toLoad){
		//Set the instance's references to the passed arguments
		this.busService = busService;
		this.toLoad = toLoad;
	}
		
	/**
	 * This method loads and instantates each class, adds them to the <code>classMap</code>, then registers them for
	 * <code>T2 Event</code>s. This is called during {@link com.flamingOctoIronman.FlamingOctoIronman#init()}.
	 */
	public void initialize(){
		//Create new objects
		classLoader = ServiceLoader.load(toLoad);	//Load the managers
		classMap = new HashMap<String, T1>();	//Create a new HashMap
		
		//Add the simple name of each manager and each manager into the classMap
		T1 manager;	//Temporary reference
		Iterator<T1> iterator = classLoader.iterator();	//Get a new Iterator from the ServiceLoader
		while(iterator.hasNext()){	//While there's Managers left in the Iterator
			manager = iterator.next();	//Set the temporary reference to the next Manager
			classMap.put(manager.getName(), manager);	//And put the simple name of the Manager and the Manager into the classMap
		}
		
		//Register each Manager for events
		Event event;	//Temporary reference
		Iterator<Event> iterator2 = (Iterator<Event>) busService.getEventIterator();	//Get a new Iterator from the ServiceLoader
		while(iterator2.hasNext()){		//While there's Events left in the Iterator
			event = iterator2.next();	//Set the temporary reference to the next Event
			for(T1 manager2 : classMap.values()){	//For each Manager in the classMap
				checkMethodsAndSubscribe(manager2, event);	//Check for methods that subscribe to the Event and subscribe them
			}
			
		}
	}
	
	/**
	 * This method registers any submanagers for <code>Event</code>s.
	 */
	public void postInitialize(){
		//Register submanagers for Events
		Event event;	//Temporary reference
		Iterator<Event> iterator = (Iterator<Event>) busService.getEventIterator();//Get a new Iterator from the ServiceLoader
		while(iterator.hasNext()){	//While there's Events left in the Iterator
			event = iterator.next();	//Set the temporary reference to the next Event
			for(T1 manager : classMap.values()){	//For each Manager in the classMap
				if(manager.getSubManagers() != null){	//If they have submanagers
					for(Object object : manager.getSubManagers()){	//Go through each submanager
						checkMethodsAndSubscribe(object, event);	//Subscribe any annotated methods to their respective Events
					}
				}
			}
			
		}
	}
	
	/**
	 * Private method to check if methods in an <code>Object</code> are annotated for the <code>Event>, 
	 * and if they are, subscribe them
	 * @param object	The <code>Object</code> that contains the methods to be checked
	 * @param event	The <code>Event</code> the check the methods against
	 */
	private void checkMethodsAndSubscribe(Object object, Event event){
		for(Method method : object.getClass().getMethods()){	//For each method in each manager
			if(method.isAnnotationPresent(EventHandler.class)){	//If the name of the annotation on the method is equal to the event's handler
				if(event.getClass().getSimpleName().equals(method.getAnnotation(EventHandler.class).event())){	//If the event parameter is equal to the event's name
					event.subscribe(method, object);	//Then subscribe the method and the object to the event
				}
			}
		}
	}
	
	/**
	 * Returns the <code>Manager</code> based on the simple class name passed
	 * @param simplifiedName The simple name of a <code>Manager</code>'s class
	 * @return	A <code>Manager</code> with the simple name <code>simplifiedName</code>
	 */
	public T1 getManager(String simplifiedName){
		return classMap.get(simplifiedName);
	}
}
