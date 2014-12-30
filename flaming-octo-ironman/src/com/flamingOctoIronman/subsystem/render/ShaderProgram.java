package com.flamingOctoIronman.subsystem.render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

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
	private HashMap<String, Shader> shaders;
	
	/**
	 * Creates a new <code>ShaderProgram</code>
	 */
	public ShaderProgram(){
		this.shaders = new HashMap<String, Shader>(2);	//2 shaders are usually used
		this.programID = GL20.glCreateProgram();	//Create a new program, store ID in programID
	}
	
	/**
	 * Creates a new <code>ShaderProgram</code> 
	 * @param shaders	A list of <code> to add to the program
	 */
	public ShaderProgram(Shader... shaders){
		this.shaders = new HashMap<String, Shader>(shaders.length);	//2 shaders are usually used
		this.programID = GL20.glCreateProgram();	//Create a new program, store ID in programID
		
		for(Shader shader : shaders){	//Loop through the passed shaders
			this.addShader(shader);	//And add them to the list of shaders
		}
	}

	public ShaderProgram clone(){
		ShaderProgram program = new ShaderProgram();
		for(Entry<String, Shader> shader : this.getShaders()){
			program.setShader(shader.getValue());
		}
		
		return program;
	}
	
	/**
	 * Adds a <code>Shader</code> to the list of <code>Shader</code>
	 * @param shader	The <code>Shader</code> to add
	 */
	public void addShader(Shader shader){
		this.shaders.put(shader.getName(), shader);
	}
	
	/**
	 * Compiles the complete shader program
	 */
	public void compileProgram(){
		Iterator<Entry<String, Shader>> iterator = shaders.entrySet().iterator();
		while(iterator.hasNext()){	//Loop through the shaders
			GL20.glAttachShader(this.programID, iterator.next().getValue().getShaderID());	//Add the shader to the program
		}
		GL20.glLinkProgram(this.programID);	//Link the shader programs
		
		//Error checking
		if(GL20.glGetProgrami(this.programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Failure in linking program. Error log:\n" + GL20.glGetProgramInfoLog(this.programID, GL20.glGetProgrami(this.programID, GL20.GL_INFO_LOG_LENGTH)));	//Print the error
			FlamingOctoIronman.getInstance().stopGame(DeathReason.SHADER_COMPILE_ERROR);	//And shutdown the game
		}
		
		//Detach and remove the shaders
		iterator = shaders.entrySet().iterator();
		while(iterator.hasNext()){	//Loop through the shaders
			GL20.glDetachShader(this.programID, iterator.next().getValue().getShaderID());	//Detach the shader from the program
			GL20.glDeleteShader(iterator.next().getValue().getShaderID());	//Delete the shader from the memory
		}
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
	
	public Shader getShader(String name){
		return this.shaders.get(name);
	}
	
	public void setShader(Shader shader){
		this.shaders.put(shader.getName(), shader);
	}
	
	public Set<Entry<String, Shader>> getShaders(){
		return this.shaders.entrySet();
	}
	
	public String modifyGLSLDefine(Shader shader, String defineName, String value){;
		String[] lines = shader.getProgram().split("\n");
		for(int i = 0; i < lines.length; i++){
			if(lines[i].contains("#define " + defineName + " ")){
				lines[i] = "#define " + defineName + " " + value + "";
			}
		}
		
		String newShader = "";
		for(int i = 0; i < lines.length; i++){
			newShader += lines[i] + "\n";
		}
		
		return newShader;
	}
	
	public String modifyGLSLDefine(String shader, String defineName, String value){;
		String[] lines = shader.split("\n");
		for(int i = 0; i < lines.length; i++){
			if(lines[i].contains("#define " + defineName + " ")){
				lines[i] = "#define " + defineName + " " + value + "";
			}
		}
	
		String newShader = "";
		for(int i = 0; i < lines.length; i++){
			newShader += lines[i] + "\n";
		}
	
		return newShader;
	}
	
	public ShaderProgram modifyGLSLDefine(Shader shader, HashMap<String, String> namesAndValues){
		Iterator<Entry<String, String>> iterator = namesAndValues.entrySet().iterator();
		String modifiedShader = shader.getProgram();
		while(iterator.hasNext()){
			Entry<String, String> keyValuePair = iterator.next();
			modifiedShader = modifyGLSLDefine(modifiedShader, keyValuePair.getKey(), keyValuePair.getValue());
		}
		
		ShaderProgram newProgram = new ShaderProgram();
		newProgram.addShader(new Shader(modifiedShader, shader.getName(), this.getShader(shader.getName()).getType()));
		for(Entry<String, Shader> program : this.getShaders()){
			if(!program.getKey().equals(shader.getName())){
				newProgram.addShader(program.getValue());
			}
		}
		
		newProgram.compileProgram();
		
		return newProgram;
		
	}
}
