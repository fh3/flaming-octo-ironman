package com.flamingOctoIronman.subsystem.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.PixelFormat;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.core.event.CoreEventHandler;
import com.flamingOctoIronman.subsystem.debugging.DebuggingManager;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class RenderEngine2{
	private int vbo;
	private ShaderProgram program;
	
	private static RenderEngine2 instance;
	
	private final float[] data = {
			0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, -0.25f, -1.25f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, -0.25f, -1.25f, 1.0f,
			-0.25f, -0.25f, -1.25f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, 0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -2.75f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -2.75f, 1.0f,
			-0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			-0.25f, -0.25f, -1.25f, 1.0f,
			-0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			-0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -2.75f, 1.0f,
			0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			0.25f, -0.25f, -1.25f, 1.0f,
			0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, 0.25f, -2.75f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			0.25f, 0.25f, -2.75f, 1.0f,
			0.25f, 0.25f, -1.25f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			0.25f, 0.25f, -2.75f, 1.0f,
			-0.25f, 0.25f, -1.25f, 1.0f,
			-0.25f, 0.25f, -2.75f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, -0.25f, -1.25f, 1.0f,
			0.25f, -0.25f, -1.25f, 1.0f,
			0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, -0.25f, -2.75f, 1.0f,
			-0.25f, -0.25f, -1.25f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.8f, 0.8f, 0.8f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			0.5f, 0.5f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 1.0f
			};
	
	//Shader uniforms and data
	private int offsetUniform;	//The position of the camera offset variable in the VRAM
	private int cameraToClipUniform;
	private float[] cameraToClipMatrix = new float[16];
	private int modelToCameraUniform;
	private float[] modelToCameraMatrix = new float[16];
	private int cameraUniform;
	private float[] cameraMatrix = new float[16];
	
	//Camera offsets
	private float xOffset;
	private float yOffset;
	private float zOffset;
	
	
	private float frustumScale = calculateFrustumScale(90f);
	
	private StreamManager out;
	
	private RenderEngine2(){
		
		//Setup display
		ContextAttribs cattr;	//The OpenGL version
		PixelFormat pfmt = new PixelFormat();	//WTF is a PixelFormat
		
		//Set the OpenGL version
		//Mac OS X requires OpenGL 3.2 to be requested, although it delivers a "core" versions of it
		if(System.getProperty("os.name").equals("Mac OS X")){
			cattr = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);	
		}else{
			cattr = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);	//This sets the OpenGL version
		}
		
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
        
        try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the renderer
	 */
	@SuppressWarnings("unused")
	@CoreEventHandler(event = "StartUpEvent")
	public void InitializeVertexBuffer(){
		out = ((DebuggingManager) FlamingOctoIronman.getInstance().getCoreManager(DebuggingManager.class.getSimpleName())).getStreamManager();
		
		//Clearing the screen
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);	//Set the color that will be used when clearing the screen
		
		//Setup the shader program
		program = new ShaderProgram();
		program.addShader(new Shader(ResourceManager.getFileDir("shaders/vertex_test.glsl"), GL20.GL_VERTEX_SHADER));	//Add the vertex shader
		program.addShader(new Shader(ResourceManager.getFileDir("shaders/frag_test.glsl"), GL20.GL_FRAGMENT_SHADER));	//Add the fragment shader
		program.compileProgram();//Compile the program
		
		//Get uniforms
		offsetUniform = GL20.glGetUniformLocation(program.getProgram(), "offset");	//Get the location of the "offset" variable in the VRAM and store it for later use
		cameraToClipUniform = GL20.glGetUniformLocation(program.getProgram(), "cameraToClipMatrix");
		modelToCameraUniform = GL20.glGetUniformLocation(program.getProgram(), "modelToCameraMatrix");
		cameraUniform = GL20.glGetUniformLocation(program.getProgram(), "cameraMatrix");
		
		//Far and near positions
		float zNear = 1.0f;
		float zFar = 45.0f;
		
		//Compute the camera space to clip space matrix
		cameraToClipMatrix[0] = frustumScale;
		cameraToClipMatrix[5] = frustumScale;
		cameraToClipMatrix[10] = (zFar + zNear) / (zNear - zFar);
		cameraToClipMatrix[14] = (2 * zFar * zNear) / (zNear - zFar);
		cameraToClipMatrix[11] = -1;
		
		//Setup the model matrix TODO change this
		modelToCameraMatrix[0] = 1.0f;
		modelToCameraMatrix[5] = 1.0f;
		modelToCameraMatrix[10] = 1.0f;
		modelToCameraMatrix[15] = 1.0f;
		
		//Setup the camera
		cameraMatrix[0] = 1.0f;
		cameraMatrix[5] = 1.0f;
		cameraMatrix[10] = 1.0f;
		cameraMatrix[15] = 1.0f;
		
		//Run the program once
		program.startProgram();
		
		//Putting data into the shaders
		GL20.glUniformMatrix4(cameraToClipUniform, false, createFloatBuffer(cameraToClipMatrix));
		GL20.glUniformMatrix4(modelToCameraUniform, false, createFloatBuffer(modelToCameraMatrix));
		
		//Stop the program
		program.stopProgram();
		
		//FloatBuffer tut02 =  createFloatBuffer(vertexData);
		vbo = GL15.glGenBuffers();		
		
		//Setup the triangle to be rendered		
		vbo = this.createVBO(createFloatBuffer(data), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
		
		//Create a Vertex Array Object
		int VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		//Setup culling
		GL11.glEnable(GL11.GL_CULL_FACE);	//Enable culling
		GL11.glCullFace(GL11.GL_BACK);	//Set the face to cull as the back
		GL11.glFrontFace(GL11.GL_CW);	//Set the front face as the points in counterclockwise direction
		
		//Setup depth testing
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthRange(0.0f, 1.0f);
		
	}
	
	/**
	 * Perform render operations
	 */
	@CoreEventHandler(event = "GameLoopEvent")
	public void render(){
		//Clear the screen, color and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//Run the shader program
		program.startProgram();
		
		GL20.glUniformMatrix4(cameraToClipUniform, false, createFloatBuffer(cameraToClipMatrix));
		GL20.glUniformMatrix4(cameraUniform, false, createFloatBuffer(cameraMatrix));
		
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);
		
		//Set the offset variable
		GL20.glUniform3f(offsetUniform, yOffset, xOffset, zOffset);
		
		//Prepare the VBO for drawing
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);	//Bind the VBO
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attrib 0 is a vec4
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, data.length * 2);	//Attrib 1 is a vec4, offset of data.length * 2
		
		//Draw the triangles
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 36);
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		//Unbind the buffer
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		//Deselect the shader program
		GL20.glUseProgram(0);
		
		//Handle display resizing
		if(Display.wasResized()){
			this.resizeDisplay();
		}
		
		//Handle display close requests
		if(Display.isCloseRequested()){
			FlamingOctoIronman.getInstance().stopGame(DeathReason.NORMAL);
		}
		
		//Update the display
		Display.update();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			cameraMatrix[14] += 0.01f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			cameraMatrix[14] -= 0.01f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			cameraMatrix[12] += 0.01f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			cameraMatrix[12] -= 0.01f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			cameraMatrix[13] += 0.01f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			cameraMatrix[13] -= 0.01f;
		}
		
		//out.println(Mouse.getX() - Display.getWidth() / 2);
		//out.println(Mouse.getY() - Display.getHeight() / 2);
	}
	
	public void resizeDisplay(){
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());	//Change the viewport size to the current window size
		cameraToClipMatrix[0] = frustumScale / ((float)Display.getWidth() / Display.getHeight());	//Adjust the perspective matrix
		program.startProgram();	//Run the program
		GL20.glUniformMatrix4(cameraToClipUniform, false, createFloatBuffer(cameraToClipMatrix));	//Update the perspective matrix in the VRAM
		program.stopProgram();	//Stop the program
	}
	
	/**
	 * Create a new <code>FloatBuffer</code> from a list of vertices
	 * @param vertices	The list of points to create the buffer from
	 * @return	A <code>FloatBuffer</code> created from the input points
	 */
	public static FloatBuffer createFloatBuffer(float[] vertices){
		return (FloatBuffer)BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();	//Create a float buffer, put data into it, flip it, return it
	}
	
	//TODO class names are weird, may need to edit switch statement
	public int createVBO(Buffer buffer, int bufferType, int usage){
		int vbo = GL15.glGenBuffers();	//Gen the vertex buffer object
		GL15.glBindBuffer(bufferType, vbo);	//Bind the buffer
		switch(buffer.getClass().getName()){
			case "java.nio.ByteBuffer":
				GL15.glBufferData(bufferType, (ByteBuffer)buffer, usage);	//Add the data to the buffer
				break;
			case "java.nio.ShortBuffer":
				GL15.glBufferData(bufferType, (ShortBuffer)buffer, usage);	//Add the data to the buffer
				break;
			case "java.nio.IntBuffer":
				GL15.glBufferData(bufferType, (IntBuffer)buffer, usage);	//Add the data to the buffer
				break;
			//case "java.nio.LongBuffer":		Apparently this isn't supported
			//	GL15.glBufferData(bufferType, (LongBuffer)buffer, usage);	//Add the data to the buffer
			//	break;
			case "java.nio.DirectFloatBufferU":
				GL15.glBufferData(bufferType, (FloatBuffer)buffer, usage);	//Add the data to the buffer
				break;
			case "java.nio.DoubleBuffer":
				GL15.glBufferData(bufferType, (DoubleBuffer)buffer, usage);	//Add the data to the buffer
				break;
			default:
				return -1;	//Return an error code
		
		}
		
		GL15.glBindBuffer(bufferType, 0);	//Unbind the buffer
		
		return vbo;	//Return the VBO
	}
		
	/**
	 * Calculates the Frustrum scale from a given angle
	 * @param angle The Frustrum angle
	 * @return	the Frustrum scale
	 */
	private static float calculateFrustumScale(float angle) {
		return 1 / (float)Math.tan((angle * (float)Math.PI / 180) / 2f);
	}
	
	/**
	 * Get the <code>RenderEngine2</code> instance
	 * @return	The instance of this class
	 */
	public static RenderEngine2 getInstance(){
		if(instance == null){	//If the instance hasn't been initialized
			instance = new RenderEngine2();		//Then create it
		}
		return instance;	//And return it
	}
}
