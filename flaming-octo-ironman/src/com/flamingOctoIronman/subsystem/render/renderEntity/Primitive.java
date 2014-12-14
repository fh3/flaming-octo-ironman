package com.flamingOctoIronman.subsystem.render.renderEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flamingOctoIronman.subsystem.resource.BufferBuilder;

public class Primitive extends RenderEntity3D {
	private final int indexCount;
	private final int GL_PRIMITIVE_TYPE;
	private static int VAO;
	private final ColorMethod method;
	private static int colorTypeUniform;
	
	public Primitive(float[] buffer, int indexCount, int GL_PRIMITIVE_TYPE, ColorMethod method) {
		super(RenderEntity3D.createVBO(BufferBuilder.createFloatBuffer(buffer), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW));
		this.indexCount = indexCount;
		this.GL_PRIMITIVE_TYPE = GL_PRIMITIVE_TYPE;
		this.method = method;
	}
	
	@Override
	public void renderObject() {
		GL20.glUniform1i(colorTypeUniform, method.getColorType());
		//Prepare the VBO for drawing
		bindVBO();
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(method.getLocation());	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);	//Attribute for vertex position 
		GL20.glVertexAttribPointer(method.getLocation(), method.getSize(), GL11.GL_FLOAT, false, 0, indexCount * 3 * 4);	//Attribute for UV position, offset of number of vertices * 4 (xyzw)
		
		//Draw the triangles
		GL11.glDrawArrays(GL_PRIMITIVE_TYPE, 0, indexCount);
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(method.getLocation());
		
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
	
	public static void setColorTypeUniform(int colorTypeUniform){
		Primitive.colorTypeUniform = colorTypeUniform;
	}
}
