package com.flamingOctoIronman.subsystem.render;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class VisualObject {
	private float[] verticies;
	private int vbo = -1;
	
	public VisualObject(float[] verticies){
		this.verticies = verticies;
		this.vbo = createVBO(RenderEngine2.createFloatBuffer(this.verticies), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
	}
	
	public int getVBO(){
		return vbo;
	}
	
	public void renderObject(){
		//Prepare the VBO for drawing
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);	//Bind the VBO
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attrib 0 is a vec4
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, this.verticies.length * 2);	//Attrib 1 is a vec4, offset of data.length * 2
		
		//Draw the triangles
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.verticies.length / 8);
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		//Unbind the buffer
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	public static int createVBO(Buffer buffer, int bufferType, int usage){
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
}
