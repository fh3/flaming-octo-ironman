package com.flamingOctoIronman.subsystem.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.flamingOctoIronman.DeathReason;
import com.flamingOctoIronman.FlamingOctoIronman;

/**
 * Handles everything shader program related.
 * @author Quint
 *
 */
public class ShaderProgram {
	/**
	 * The ID of the shader program
	 */
	private int programID;
	/**
	 * The list of shaders
	 */
	private ArrayList<Integer> shaders;
	
	/**
	 * Creates a new <code>ShaderProgram</code>
	 */
	public ShaderProgram(){
		this.shaders = new ArrayList<Integer>(2);	//2 shaders are usually used
		this.programID = GL20.glCreateProgram();	//Create a new program, store ID in programID
	}
	
	/**
	 * Creates a new <code>ShaderProgram</code> 
	 * @param shaders	A list of <code> to add to the program
	 */
	public ShaderProgram(Shader... shaders){
		this.shaders = new ArrayList<Integer>(2);	//2 shaders are usually used
		this.programID = GL20.glCreateProgram();	//Create a new program, store ID in programID
		
		for(Shader shader : shaders){	//Loop through the passed shaders
			this.addShader(shader);	//And add them to the list of shaders
		}
	}
	
	/**
	 * Adds a <code>Shader</code> to the list of <code>Shader</code>
	 * @param shader	The <code>Shader</code> to add
	 */
	public void addShader(Shader shader){
		this.shaders.add(shader.getShaderID());
	}
	
	/**
	 * Compiles the complete shader program
	 */
	public void compileProgram(){
		for(int shader: shaders){	//Loop through the shaders
			GL20.glAttachShader(this.programID, shader);	//Add the shader to the program
		}
		GL20.glLinkProgram(this.programID);	//Link the shader programs
		
		//Error checking
		if(GL20.glGetProgrami(this.programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Failure in linking program. Error log:\n" + GL20.glGetProgramInfoLog(this.programID, GL20.glGetProgrami(this.programID, GL20.GL_INFO_LOG_LENGTH)));	//Print the error
			FlamingOctoIronman.getInstance().stopGame(DeathReason.SHADER_COMPILE_ERROR);	//And shutdown the game
		}
		
		//Detach and remove the shaders
		for(int shader : this.shaders){	//Loop through the shaders
			GL20.glDetachShader(this.programID, shader);	//Detach the shader from the program
			GL20.glDeleteShader(shader);	//Delete the shader from the memory
		}
		
		//Set the shader references to null
		this.shaders = null;
	}
		
	/**
	 * Returns the OpenGL program ID
	 * @return	the OpenGL program ID
	 */
	public int getProgram(){
		return this.programID;
	}
	
	/**
	 * Starts the program
	 */
	public void startProgram(){
		GL20.glUseProgram(this.programID);
	}
	
	/**
	 * Stops the program
	 */
	public void stopProgram(){
		GL20.glUseProgram(0);
	}
}
