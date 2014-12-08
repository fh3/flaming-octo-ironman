package com.flamingOctoIronman.subsystem.resource;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class BufferBuilder {
	
	/**
	 * Create a new <code>FloatBuffer</code> from a list of vertices
	 * @param vertices	The list of points to create the buffer from
	 * @return	A <code>FloatBuffer</code> created from the input points
	 */
	public static FloatBuffer createFloatBuffer(float[] vertices){
		return (FloatBuffer)BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();	//Create a float buffer, put data into it, flip it, return it
	}
	
	public static FloatBuffer createFloatBuffer(Matrix4f matrix){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);	//Create the buffer
		matrix.store(buffer);	//Store the matrix in it
		buffer.flip();		//Flip the buffer
		return buffer;	//Return the buffer
	}
	
	public static FloatBuffer createFloatBuffer(Vector3f vector){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		vector.store(buffer);
		buffer.flip();
		return buffer;
	}
	
	public static ByteBuffer createByteBuffer(byte[] bytes){
		return (ByteBuffer) BufferUtils.createByteBuffer(bytes.length).put(bytes).flip();
	}
}
