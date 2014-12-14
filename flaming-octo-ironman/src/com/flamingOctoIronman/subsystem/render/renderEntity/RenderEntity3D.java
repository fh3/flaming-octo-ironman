package com.flamingOctoIronman.subsystem.render.renderEntity;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

public abstract class RenderEntity3D {
	private final int vbo;
	
	public RenderEntity3D(int vbo){
		this.vbo = vbo;
	}
	
	public int getVBO(){
		return vbo;
	}
	
	public void bindVBO(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}
	
	public static void unbindVBO(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public abstract void renderObject();
	
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
