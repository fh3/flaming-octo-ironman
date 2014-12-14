package com.flamingOctoIronman.subsystem.render.primitives;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flamingOctoIronman.subsystem.resource.BufferBuilder;

public class Primitive extends RenderEntity3D {
	private final int indexCount;
	private final int GL_PRIMITIVE_TYPE;
	private static int VAO;
	private final FloatBuffer fb;
	private final float[] f = new float[216];
	
	public Primitive(float[] buffer, int indexCount, int GL_PRIMITIVE_TYPE) {
		super(RenderEntity3D.createVBO(BufferBuilder.createFloatBuffer(buffer), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW));
		fb = BufferBuilder.createFloatBuffer(buffer);
		fb.get(f);
		this.indexCount = indexCount;
		this.GL_PRIMITIVE_TYPE = GL_PRIMITIVE_TYPE;
	}
	@Override
	public void renderObject() {
		//Prepare the VBO for drawing
		bindVBO();
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attribute for vertex position 
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, indexCount * 4 * 4);	//Attribute for UV position, offset of number of vertices * 4 (xyzw)
		
		//Draw the triangles
		GL11.glDrawArrays(GL_PRIMITIVE_TYPE, 0, indexCount);
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		//Unbind the buffer
		unbindVBO();
	}

	public static void bindVAO(){
		GL30.glBindVertexArray(VAO);
	}

	public static void unbindVAO(){
		GL30.glBindVertexArray(0);
	}

	public static void setVAO(int VAO){
		Primitive.VAO = VAO;
	}
}
