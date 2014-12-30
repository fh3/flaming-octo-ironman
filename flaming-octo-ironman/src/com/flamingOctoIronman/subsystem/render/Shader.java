package com.flamingOctoIronman.subsystem.render;

import java.io.File;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

/**
 * Loads and handles shaders. When constructed, this class will compile the shader and stop the game if it failed to compile.
 * @author Quint
 *
 */
public class Shader {
	/**
	 * Holds OpenGL's ID for the shader
	 */
	private int shaderID;
	/**
	 * OpenGL's "type" of shader
	 */
	private int type;
	
	private String name;
	private String program;
	
	/**
	 * Loads and compiles a new shader object
	 * @param program	The source of the program to load
	 * @param type	The type of shader this is
	 */
	public Shader(File program, int type){
		this(ResourceManager.ReadFile(program), program.getName(), type);
	}
	
	public Shader(String program, String shaderName, int type){
		this.shaderID = GL20.glCreateShader(type);
		this.program = program;
		this.name = shaderName;
		this.type = type;
		
		setShader(this.program);
	}
	
	public void setShader(String shader){
		GL20.glShaderSource(this.shaderID, shader);	//Load the shader source
		GL20.glCompileShader(this.shaderID);	//Compile the shader
		
		//Error checking
		if(GL20.glGetShaderi(this.shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {	//If there was an error
			System.err.println("Failure in compiling "+ this.name +". Error log:\n" + GL20.glGetShaderInfoLog(this.shaderID, GL20.glGetShaderi(this.shaderID, GL20.GL_INFO_LOG_LENGTH)));	//Print the error
			FlamingOctoIronman.getInstance().stopGame(DeathReason.SHADER_COMPILE_ERROR);	//And shutdown the game
		}
	}
	
	/**
	 * Returns the OpenGL ID for the shader
	 * @return	The OpenGL ID for the shader
	 */
	public int getShaderID(){
		
		return shaderID;
	}
	
	/**
	 * Returns the OpenGL shader type
	 * @return	The OpenGL shader type
	 */
	public int getType(){
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	public String getProgram(){
		return this.program;
	}
}
