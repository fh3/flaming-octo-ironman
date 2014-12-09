package com.flamingOctoIronman.subsystem.render.primitives;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flamingOctoIronman.subsystem.resource.BufferBuilder;

public class Primitive extends RenderEntity3D {
	private final int verticiesLength;
	private final int GL_PRIMITIVE_TYPE;
	private static int VAO;
	
	public Primitive(float[] buffer, int GL_PRIMITIVE_TYPE) {
		super(RenderEntity3D.createVBO(BufferBuilder.createFloatBuffer(buffer), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW));
		this.verticiesLength = buffer.length;
		this.GL_PRIMITIVE_TYPE = GL_PRIMITIVE_TYPE;
		System.out.println(buffer.length * (2/3));
	}
	@Override
	public void renderObject() {
		//Prepare the VBO for drawing
		bindVBO();
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attrib 0 is a vec4
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, verticiesLength * (2 / 3));	//Attrib 1 is a vec4, offset of data.length * 2
		
		//Draw the triangles
		GL11.glDrawArrays(GL_PRIMITIVE_TYPE, 0, verticiesLength * (2/3));
		
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
