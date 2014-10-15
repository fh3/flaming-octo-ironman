package com.flamingOctoIronman.subsystem.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.subsystem.debugging.DebuggingManager;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;

/**
 * This class contains and handles the game.
 * @author Quint Heinecke
 *
 */
public class RenderEngine {
	/**
	 * The sole instance of this class
	 */
	private static RenderEngine instance;
	
	private StreamManager out = ((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(DebuggingManager.class.getSimpleName())).getStreamManager();
	
	/**
	 * The default {@link PixelFormat}
	 */
	private PixelFormat pfmt = new PixelFormat();	//WTF is a pixelformat
	
	private RenderEngine(){
		// Create the default PixelFormat
        

        // We need a core context with atleast OpenGL 3.2
        ContextAttribs cattr = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

        // Create the Display
        try {
			Display.create(pfmt, cattr);
		} catch (LWJGLException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
		}
        Display.setResizable(true);
	}
	
	/**
	 * Gets the instance of this class
	 * @return	This class's instance
	 */
	public static RenderEngine getInstance(){
		if(instance == null){	//If an instance hasn't been created
			instance = new RenderEngine();	//Create it
		}
		return instance;	//Return the instance
	}
}
