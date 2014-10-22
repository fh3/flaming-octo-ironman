package com.flamingOctoIronman.subsystem.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flamingOctoIronman.core.event.CoreEventHandler;

public class RenderEngine2 {
	float vertexPositions[] = {
		    0.75f, 0.75f, 0.0f, 1.0f,
		    0.75f, -0.75f, 0.0f, 1.0f,
		    -0.75f, -0.75f, 0.0f, 1.0f,
		};
	FloatBuffer vertexBuffer = createFloatBuffer(vertexPositions);
	int positionBuffer;
	
	@CoreEventHandler(event = "StartUpEvent")
	void InitializeVertexBuffer()
	{
	    this.positionBuffer = GL15.glGenBuffers();
	    
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBuffer);
	    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	@CoreEventHandler(event = "GameLoopEvent")
	void render(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.positionBuffer);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
	}
	
	public static FloatBuffer createFloatBuffer(float[] vertices){
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.length);
		return (FloatBuffer)vertexData.put(vertices).flip();

	}
}
