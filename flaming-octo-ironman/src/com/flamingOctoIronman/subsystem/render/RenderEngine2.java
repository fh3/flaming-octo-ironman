package com.flamingOctoIronman.subsystem.render;

import java.util.ArrayList;

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
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.framework.event.EventHandler;
import com.flamingOctoIronman.subsystem.debugging.DebuggingManager;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;
import com.flamingOctoIronman.subsystem.render.renderEntity.Line;
import com.flamingOctoIronman.subsystem.render.renderEntity.Point;
import com.flamingOctoIronman.subsystem.render.renderEntity.Primitive;
import com.flamingOctoIronman.subsystem.render.renderEntity.RenderEntity3D;
import com.flamingOctoIronman.subsystem.render.renderEntity.Triangle;
import com.flamingOctoIronman.subsystem.resource.BufferBuilder;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class RenderEngine2{
	private ShaderProgram program;
	
	private static RenderEngine2 instance;
	
	private ArrayList<Primitive> primitiveList;
	private ArrayList<Model> OBJEntityList;
	
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
	
	private float[] r2 = {
			0.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			
			1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f,
			
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 1.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f
	};
		
	//Shader uniforms and data
	private int perspectiveMatrixUniform;
	private float[] perspectiveMatrix = new float[16];
	private int modelMatrixUniform;
	private float[] modelMatrix = new float[16];
	private int viewMatrixUniform;
	private int colorTypeUniform;

	//Camera data
	private final float defaultXAngle = 0.0f;
	private final float defaultYAngle = 0.0f;
	private final float defaultZAngle = 0.0f;
	private float xAngle = defaultXAngle;
	private float yAngle = defaultYAngle;
	private float zAngle = defaultZAngle;
	private Vector3f forward = new Vector3f();
	private Vector3f side = new Vector3f();
	private Vector3f up = new Vector3f();
	private Matrix4f viewMatrix = new Matrix4f();
	private Vector3f translateVector = createVector(0.0f, 0.0f, 0.0f);
	private Vector3f lookVector = new Vector3f();
	private float rotateRate = 0.01f;
	private float translateRate = 0.1f;
	
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
        
        primitiveList = new ArrayList<Primitive>();
        OBJEntityList = new ArrayList<Model>();
	}
	
	/**
	 * Initialize the renderer
	 */
	@SuppressWarnings("static-access")
	@EventHandler(event = "StartUpEvent")
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
		perspectiveMatrixUniform = GL20.glGetUniformLocation(program.getProgram(), "perspectiveMatrix");
		modelMatrixUniform = GL20.glGetUniformLocation(program.getProgram(), "modelMatrix");
		viewMatrixUniform = GL20.glGetUniformLocation(program.getProgram(), "viewMatrix");
		colorTypeUniform = GL20.glGetUniformLocation(program.getProgram(), "colorType");
		
		//Far and near positions
		float zNear = 1.0f;
		float zFar = 45.0f;
		
		//Compute the camera space to clip space matrix
		perspectiveMatrix[0] = frustumScale;
		perspectiveMatrix[5] = frustumScale;
		perspectiveMatrix[10] = (zFar + zNear) / (zNear - zFar);
		perspectiveMatrix[14] = (2 * zFar * zNear) / (zNear - zFar);
		perspectiveMatrix[11] = -1;
		
		//Setup the model matrix TODO change this
		modelMatrix[0] = 1.0f;
		modelMatrix[5] = 1.0f;
		modelMatrix[10] = 1.0f;
		modelMatrix[15] = 1.0f;
		
		//Run the program once
		program.startProgram();
		
		//Putting data into the shaders
		GL20.glUniformMatrix4(perspectiveMatrixUniform, false, BufferBuilder.createFloatBuffer(perspectiveMatrix));
		GL20.glUniformMatrix4(modelMatrixUniform, false, BufferBuilder.createFloatBuffer(modelMatrix));
		
		//Stop the program
		program.stopProgram();
		
		//Setup the triangle to be rendered	
		//primitiveList.add(OBJLoader.loadObject(ResourceManager.getFileDir("objects/testing/test.obj")));
		//GL11.glLineWidth(1.0f);
		//primitiveList.add(new Primitive(r2, GL11.GL_LINES));
		
		//Create a Vertex Array Object
		int VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		//Setup culling
		//GL11.glEnable(GL11.GL_CULL_FACE);	//Enable culling
		GL11.glCullFace(GL11.GL_BACK);	//Set the face to cull as the back
		GL11.glFrontFace(GL11.GL_CCW);	//Set the front face as the points in counterclockwise direction
		
		//Setup depth testing
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthRange(0.0f, 1.0f);
		
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		OBJEntityList.add(new Model(ResourceManager.getFileDir("objects/testobject3.obj"), ResourceManager.getFileDir("textures/quintneedsthis.bmp")));
		primitiveList.add(new Point(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), 10.0f));
		primitiveList.add(new Line(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(10.0f, 0.0f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), 2.0f));
		primitiveList.add(new Line(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 10.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), 2.0f));
		primitiveList.add(new Line(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 10.0f), new Vector3f(0.0f, 0.0f, 1.0f), 2.0f));
		primitiveList.add(new Triangle(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(1.0f, 0.0f, 1.0f)));
		primitiveList.add(new Point(new Vector3f(5.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), 2.0f));

		out.println("Done loading");
		
		Primitive.setColorTypeUniform(colorTypeUniform);
		Primitive.setVAO(VAO);
	}
	
	/**
	 * Perform render operations
	 */
	@EventHandler(event = "GameLoopEvent")
	public void render(){
		//primitiveList.set(4, new Point(translateVector, new Vector3f(1.0f, 1.0f, 0.0f), 20.0f));
		//Clear the screen, color and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		viewMatrix = createAtomicTransformationMatrix(xAngle, yAngle, zAngle, translateVector);

		//Run the shader program
		program.startProgram();
		
		GL20.glUniformMatrix4(perspectiveMatrixUniform, false, BufferBuilder.createFloatBuffer(perspectiveMatrix));
		GL20.glUniformMatrix4(viewMatrixUniform, false, BufferBuilder.createFloatBuffer(viewMatrix));
		
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);

		for(int i = 0; i < primitiveList.size(); i++){
			if(primitiveList.get(i) != null){
				primitiveList.get(i).renderObject();
			}
		}
		for(int i = 0; i< OBJEntityList.size(); i++){
			if(OBJEntityList.get(i) != null){
				OBJEntityList.get(i).getTexture().bind();
				OBJEntityList.get(i).getPrimitive().renderObject();
			}
		}
		
		//Deselect the shader program
		program.stopProgram();
		
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
			
		if(true){
			//xAngle = -0.001f * (Display.getWidth() / 2 - Mouse.getY());
			//yAngle = 0.001f * (Display.getHeight() / 2 - Mouse.getX());
			//out.println("X: " + Mouse.getX() + ", Y: " + Mouse.getY());
			//out.println(Display.getWidth() / 2);
			//out.println(Display.getHeight() / 2);
			//out.println(forward.toString());
			//Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		}
		//Calculate vectors
		forward = createVector(0.0f, 0.0f, 1.0f);
		forward = matrixVectorMultiplication(createRotationMatrix(0, yAngle, 0), forward);
		forward.x *= -1;
		forward.normalise();
		Vector3f.cross(forward, createVector(0, 1, 0), side);
		side.normalise();
		Vector3f.cross(forward, side, up);
		up.normalise();
		lookVector = new Vector3f();
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			Vector3f.add(forward, lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			Vector3f.add((Vector3f) forward.scale(-1), lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			Vector3f.add((Vector3f) side.scale(-1), lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			Vector3f.add(side, lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			Vector3f.add((Vector3f) up.scale(-1), lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			Vector3f.add(up, lookVector, lookVector);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yAngle -= rotateRate;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yAngle += rotateRate;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			xAngle -= rotateRate;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			xAngle += rotateRate;
		}
		try{
			lookVector.normalise();
		} catch(IllegalStateException e){
			
		}
		lookVector.scale(translateRate);
		Vector3f.add(lookVector, translateVector, translateVector);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			translateVector = new Vector3f();
			xAngle = defaultXAngle;
			yAngle = defaultYAngle;
			zAngle = defaultZAngle;
		}
	}
	
	public void resizeDisplay(){
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());	//Change the viewport size to the current window size
		perspectiveMatrix[0] = frustumScale / ((float)Display.getWidth() / Display.getHeight());	//Adjust the perspective matrix
		program.startProgram();	//Run the program
		GL20.glUniformMatrix4(perspectiveMatrixUniform, false, BufferBuilder.createFloatBuffer(perspectiveMatrix));	//Update the perspective matrix in the VRAM
		program.stopProgram();	//Stop the program
	}
		
	/**
	 * Calculates the Frustrum scale from a given angle
	 * @param angle The Frustrum angle
	 * @return	the Frustrum scale
	 */
	private static float calculateFrustumScale(float angle) {
		return 1 / (float)Math.tan((angle * (float)Math.PI / 180) / 2f);
	}
	
	public static Vector3f createVector(float x, float y, float z){
		Vector3f v = new Vector3f();
		v.x = x;
		v.y = y;
		v.z = z;
		return v;
	}
	
	public static Quaternion createQuaternion(Vector3f v, float angle){
		Quaternion q = new Quaternion();
		float halfAngle = angle / 2;
		float cosineAngle = (float)Math.cos(halfAngle);
		q.w = (float) Math.sin(halfAngle);
		q.x = v.x * cosineAngle;
		q.y = v.y * cosineAngle;
		q.z = v.z * cosineAngle;
		return q;
	}

	public static Matrix4f matrixFromQuaternion(Quaternion q){
		Matrix4f m = new Matrix4f();
		m.m00 = 1 - 2 * q.y * q.y - 2 * q.z * q.z;
		m.m10 = 2 * q.x * q.y - 2 * q.z * q.w;
		m.m20 = 2 * q.x * q.z + 2 * q.y * q.w;
		m.m01 = 2 * q.x * q.y + 2 * q.z*q.w;
		m.m11 = 1 - 2 * q.x * q.x - 2 * q.z * q.z;
		m.m21 = 2 * q.y * q.z - 2 * q.x * q.w;
		m.m02 = 2 * q.x * q.z - 2 * q.y * q.w;
		m.m12 = 2 * q.y * q.z + 2 * q.x * q.w;
		m.m22 = 1 - 2 * q.x * q.x - 2 * q.y * q.y;
		return m;
	}
	
	public Matrix4f createAtomicTransformationMatrix(Quaternion q, Vector3f translate){
		return createAtomicTransformationMatrix(q.x, q.y, q.z, translate);
	}
	
	public static Matrix4f createRotationMatrix(float x, float y, float z){
		Matrix4f rotationMatrix = new Matrix4f();
		rotationMatrix.setIdentity();
		rotationMatrix.rotate(x, new Vector3f(1.0f, 0.0f, 0.0f));
		rotationMatrix.rotate(y, new Vector3f(0.0f, 1.0f, 0.0f));
		rotationMatrix.rotate(z, new Vector3f(0.0f, 0.0f, 1.0f));
		return rotationMatrix;
	}
	
	public Matrix4f createAtomicTransformationMatrix(float x, float y, float z, Vector3f translate){
		Matrix4f rotationComplete = createRotationMatrix(x, y, z);
		Matrix4f translationMatrix = new Matrix4f();
		
		translationMatrix.setIdentity();
		translationMatrix.m30 = translate.x;
		translationMatrix.m31 = translate.y;
		translationMatrix.m32 = translate.z;
		Matrix4f.mul(rotationComplete, translationMatrix, rotationComplete);
		return rotationComplete;
	}
	
	public Vector3f matrixVectorMultiplication(Matrix4f matrix, Vector3f vector){
		Vector3f result = new Vector3f();
		result.x = matrix.m00 * vector.x + matrix.m10 * vector.y + matrix.m20 * vector.z;
		result.y = matrix.m01 * vector.x + matrix.m11 * vector.y + matrix.m21 * vector.z;
		result.z = matrix.m02 * vector.x + matrix.m12 * vector.y + matrix.m22 * vector.z;
		return result;
	}
	
	public static Matrix4f newIdentityMatrix(){
		Matrix4f m = new Matrix4f();
		Matrix4f.setIdentity(m);
		return m;		
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
