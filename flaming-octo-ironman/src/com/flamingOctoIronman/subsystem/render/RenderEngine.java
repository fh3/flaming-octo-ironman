package com.flamingOctoIronman.subsystem.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

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
	
	private int vertexCount = 0;
	
	private ArrayList<VertexArrayObject> vaoIDList;	//The list of Vertex Array Objects			//I hate using these in this class, but I don't think I have an alternative.
//Fortunately I shouldn't be putting objects in them during the render loop.
	
	private RenderEngine(){
		//This currently lets me draw up to 256 triangles before stuff starts to get slow.
        vaoIDList = new ArrayList<VertexArrayObject>(16);	//TODO Don't make this hard-coded.

        // We need a core context with atleast OpenGL 3.2
		cattr = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);	//This sets the OpenGL version
        pfmt = new PixelFormat();	//WTF is a pixelformat

        // Create the Display
        try {
        	Display.setDisplayMode(new DisplayMode(800,600));	//Set the size
			Display.create();	//Create the display
			//Display.setFullscreen(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
		}
        Display.setResizable(true);	//Make it resizeable
        Display.setTitle("FLAMING OCTO IRONMAN");	//Set the title
        
        float[] triangle = {
        		//Illuminati 
        		0f, 0.5f, 0f,
        		-0.5f, -0.5f, 0f,
        		0.5f, -0.5f, 0f,
        		};
        float[] quad = {
        		-0.5f, 0.5f, 0f,    // Left top         ID: 0
        		-0.5f, -0.5f, 0f,   // Left bottom      ID: 1
        		0.5f, -0.5f, 0f,    // Right bottom     ID: 2
        		0.5f, 0.5f, 0f  // Right left       ID: 3
        		};
        
        //this.renderTriangle(triangle);
        this.renderQuad(quad);
	}
	
	public void renderQuad(float[] vertices){
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);	//Create a FloatBuffer the size of the passed array
		verticesBuffer.put(vertices);	//Put the passed array into the buffer
        verticesBuffer.flip();	//Flip the buffer (I'm not quite sure what this does or why it's needed, but the documentation says its needed, so I call it. Little endian format maybe???)
        
        byte[] indices = {	//Indices are a way to refer to vertices w/o redefining them
				    		//Drawn in counter-clockwise order
				    		// Left bottom triangle
				    		0, 1, 2,
				    		// Right top triangle
				    		2, 3, 0
				    		};
		int indicesCount = indices.length;
		ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);	//Create a byte buffer the size of the indices array
		indicesBuffer.put(indices);	//Add teh indices array to the buffer
		indicesBuffer.flip();	//Flip the buffer
		
		int vaoId = -1;	//The current VAO
        int openSlot = -1;	//The current VBO
        for(int i = 0; i < vaoIDList.size() && openSlot == -1; i++){	//look through all the VAO's for an open slot
        	vaoId = i;
        	openSlot = vaoIDList.get(i).findEmptyVBO();
        }
        if(openSlot == -1){	//If none were found then
        	vaoIDList.add(new VertexArrayObject(GL30.glGenVertexArrays()));	//Create a new VAO
        	vaoId = vaoIDList.size() - 1;	//Set the VAO to the last one in the VAO list
        	openSlot = 0;	//And use the first VBO slot
        }
        
        GL30.glBindVertexArray(vaoIDList.get(vaoId).getAddress());	//Bind the VAO
		int vboId = GL15.glGenBuffers();	//Create a new VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);	//Select the VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);	//Add the vertices to the VBO
        GL20.glVertexAttribPointer(openSlot, 3, GL11.GL_FLOAT, false, 0, 0);	//Put the VBO in the VAO list at the open slot
        vaoIDList.get(vaoId).getUsedList()[openSlot] = GL15.GL_ARRAY_BUFFER;	//Mark the slot as used
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//Deselect the VBO
        GL30.glBindVertexArray(0);	//Deselect the VAO
		
		//For some reason I don't need a VAO for this
		// Create a new VBO for the indices and select it (bind)
		int vboiId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
		
	public void renderTriangle(float[] vertices){
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);	//Create a FloatBuffer the size of the passed array
		verticesBuffer.put(vertices);	//Put the passed array into the buffer
        verticesBuffer.flip();	//Flip the buffer (I'm not quite sure what this does or why it's needed, but the documentation says its needed, so I call it. Little endian format maybe???)
        vertexCount =+ vertices.length / 3;	//Increase the vertex count by the number of vertices (should always be 3)
        int vaoId = -1;	//The current VAO
        int openSlot = -1;	//The current VBO
        for(int i = 0; i < vaoIDList.size() && openSlot == -1; i++){	//look through all the VAO's for an open slot
        	vaoId = i;
        	openSlot = vaoIDList.get(i).findEmptyVBO();
        }
        if(openSlot == -1){	//If none were found then
        	vaoIDList.add(new VertexArrayObject(GL30.glGenVertexArrays()));	//Create a new VAO
        	vaoId = vaoIDList.size() - 1;	//Set the VAO to the last one in the VAO list
        	openSlot = 0;	//And use the first VBO slot
        }
        
        GL30.glBindVertexArray(vaoIDList.get(vaoId).getAddress());	//Bind the VAO
		int vboId = GL15.glGenBuffers();	//Create a new VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);	//Select the VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);	//Add the vertices to the VBO
        GL20.glVertexAttribPointer(openSlot, 3, GL11.GL_FLOAT, false, 0, 0);	//Put the VBO in the VAO list at the open slot
        vaoIDList.get(vaoId).getUsedList()[openSlot] = GL15.GL_ARRAY_BUFFER;	//Mark the slot as used
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//Deselect the VBO
        GL30.glBindVertexArray(0);	//Deselect the VAO
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
	
	//Remember, we care about speed over everything else here.
	@CoreEventHandler(event = "GameLoopEvent")
	public void update(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < vaoIDList.size(); i++){
			GL30.glBindVertexArray(vaoIDList.get(i).getAddress());
			for(int vboID = 0; vboID < vaoIDList.get(i).getUsedList().length; vboID++){
				if(vaoIDList.get(i).getUsedList()[i] != 0){
					GL20.glEnableVertexAttribArray(i);
					GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
					GL20.glDisableVertexAttribArray(0);
			        GL30.glBindVertexArray(0);
				}
			}
		}
		Display.update();
		
		if(Display.isCloseRequested()){
			FlamingOctoIronman.getInstance().stopGame(DeathReason.NORMAL);
		}
	}
	
	@CoreEventHandler(event = "ShutDownEvent")
	public void shutDown(){
		//Memory stuff (not sure if this is right, I might be wasting lots of memory here)
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		for(int vao = 0; vao < vaoIDList.size(); vao++){
			for(int vbo = 0; vbo < vaoIDList.get(vao).getUsedList().length; vbo++){
				GL20.glDisableVertexAttribArray(vbo);
				GL15.glDeleteBuffers(vbo);
			}
			GL30.glBindVertexArray(vao);
			GL30.glDeleteVertexArrays(vao);
		}
		
		Display.destroy();
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
