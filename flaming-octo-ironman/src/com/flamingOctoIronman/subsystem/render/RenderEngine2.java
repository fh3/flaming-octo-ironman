package com.flamingOctoIronman.subsystem.render;

import java.io.File;
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
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class RenderEngine2{
	private float vertexPositions[] = {
		    0.75f, 0.75f, 0.0f, 1.0f,
		    0.75f, -0.75f, 0.0f, 1.0f,
		    -0.75f, -0.75f, 0.0f, 1.0f
		};
	private int vbo;
	private int program;
	
	private static RenderEngine2 instance;

	private RenderEngine2(){
		//Setup display
		// We need a core context with atleast OpenGL 3.2
		ContextAttribs cattr = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);	//This sets the OpenGL version
		PixelFormat pfmt = new PixelFormat();	//WTF is a pixelformat
		
		try {
        	Display.setDisplayMode(new DisplayMode(800,600));	//Set the size
			Display.create(pfmt, cattr);	//Create the display
			//Display.setFullscreen(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			FlamingOctoIronman.getInstance().stopGame(DeathReason.EXCEPTION);
		}
        Display.setResizable(true);	//Make it resizeable
        Display.setTitle("FLAMING OCTO IRONMAN");	//Set the title
	}
	
	@CoreEventHandler(event = "StartUpEvent")
	public void InitializeVertexBuffer(){
		//Clearing the screen
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);	//Set the color that will be used when clearing the screen
		
		//Setup the shader program
		int[] shaders = new int[2];
		//Load/compile the shaders
		shaders[0] = this.loadShader(ResourceManager.getFileDir("shaders/vertex_test.glsl"), GL20.GL_VERTEX_SHADER);
		shaders[1] = this.loadShader(ResourceManager.getFileDir("shaders/frag_test.glsl"), GL20.GL_FRAGMENT_SHADER);
		//Create the program
		program = this.createProgram(shaders);
		//Remove the shaders
		this.detachShaders(program, shaders);
		
		//Setup the triangle to be rendered
		//Create convert the vertex array into a buffer
		FloatBuffer triangle = createFloatBuffer(vertexPositions);
		//Setup and initialize the buffer
		vbo = GL15.glGenBuffers();	//Create a new Vertex Buffer Object
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);	//Select the buffer to operate on
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, triangle, GL15.GL_STATIC_DRAW);	//Add the points the the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//Bind the buffer to 0 (equivalent of setting to null)
		//Create a Vertex Array Object
		GL30.glBindVertexArray(GL30.glGenVertexArrays());
	}
	
	@CoreEventHandler(event = "GameLoopEvent")
	public void render(){
		//Clear the screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		//Select the shader program to use
		GL20.glUseProgram(program);
		
		//Prepare the VBO for drawing
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);	//Bind the VBO
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location 0 in the vertex attribute array
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Cant remember what this does
		
		//Draw the triangle
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		
		//Disable the attribute at location 0
		GL20.glDisableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//Unbind the buffer
		
		//Deselect the shader program
		GL20.glUseProgram(0);
		
		//Update the display
		Display.update();
	}
	
	public static FloatBuffer createFloatBuffer(float[] vertices){
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.length);
		return (FloatBuffer)vertexData.put(vertices).flip();
	}
	
	public int loadShader(File location, int type){
		int shader = GL20.glCreateShader(type);	//Create a new shader of the passed type
		GL20.glShaderSource(shader, ResourceManager.ReadFile(location));	//Load the shader from the file into the new shader
		GL20.glCompileShader(shader);	//Compile the shader
		
		//Error checking
		if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {	//If there was an error
			System.err.println("Failure in compiling"+ location.getName() +". Error log:\n" + GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH)));	//Print the error
			FlamingOctoIronman.getInstance().stopGame(DeathReason.SHADER_COMPILE_ERROR);	//And shutdown the game
		}
		
		return shader;
	}
	
	public int createProgram(int[] shaders){
		int program = GL20.glCreateProgram();	//Create a new shader
		for(int shader: shaders){	//Loop through the shaders
			GL20.glAttachShader(program, shader);	//Add the shader to the program
		}
		GL20.glLinkProgram(program);	//Link the shader programs
		
		//Error checking
		if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Failure in linking program. Error log:\n" + GL20.glGetProgramInfoLog(program, GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH)));	//Print the error
			FlamingOctoIronman.getInstance().stopGame(DeathReason.SHADER_COMPILE_ERROR);	//And shutdown the game
		}
		
		return program;
	}
	
	public void detachShaders(int program, int[] shaders){
		for(int shader : shaders){	//Loop through the shaders
			GL20.glDetachShader(program, shader);	//Detach the shader from the program
			GL20.glDeleteShader(shader);	//Delete the shader from the memory
		}
	}
	
	public static RenderEngine2 getInstance(){
		if(instance == null){
			instance = new RenderEngine2();
		}
		return instance;
	}
}
