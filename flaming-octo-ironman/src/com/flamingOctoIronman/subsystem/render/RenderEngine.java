package com.flamingOctoIronman.subsystem.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.event.CoreEventHandler;
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
	
	private StreamManager out;
	
	private DisplayMode[] dm;
			
	/**
	 * The default {@link PixelFormat}
	 */
	private PixelFormat pfmt;
	private ContextAttribs cattr;
	
	private int vertexCount;
	private int vaoId;
	private int vboId;
	
	private RenderEngine(){
		// Create the default PixelFormat
        

        // We need a core context with atleast OpenGL 3.2
		cattr = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
        pfmt = new PixelFormat();	//WTF is a pixelformat

        // Create the Display
        try {
        	Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			//Display.setFullscreen(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
		}
        Display.setResizable(true);
        Display.setTitle("FLAMING OCTO IRONMAN");
        
        float[] vertices = {
        		// Left bottom triangle
        		-0.5f, 0.5f, 0f,
        		-0.5f, -0.5f, 0f,
        		0.5f, -0.5f, 0f,
        		// Right top triangle
        		0.5f, -0.5f, 0f,
        		0.5f, 0.5f, 0f,
        		-0.5f, 0.5f, 0f
        		};
        // Sending data to OpenGL requires the usage of (flipped) byte buffers
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        vertexCount = vertices.length / 3;
        
        // Create a new Vertex Array Object in memory and select it (bind)
        // A VAO can have up to 16 attributes (VBO's) assigned to it by default
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
         
        // Create a new Vertex Buffer Object in memory and select it (bind)
        // A VBO is a collection of Vectors which in this case resemble the location of each vertex.
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the VBO in the attributes list at index 0
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
        

	}
	
	@CoreEventHandler(event = "PostInitializationEvent")
	public void postInitialize(){
		out = ((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManagerManager().getManager(DebuggingManager.class.getSimpleName())).getStreamManager();
	}
	
	public void printDisplayModes(){
        try {
			dm = Display.getAvailableDisplayModes();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(DisplayMode mode : dm){
        	out.println(mode.toString());
        }
	}
	
	@CoreEventHandler(event = "StartUpEvent")
	public void startUp(){
	}
	
	@CoreEventHandler(event = "GameLoopEvent")
	public void update(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
         
        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
         
        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
		Display.update();
	}
	
	@CoreEventHandler(event = "ShutDownEvent")
	public void shutDown(){
		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0);
		 
		// Delete the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		 
		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
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
