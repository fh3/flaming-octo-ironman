package com.flamingOctoIronman.subsystem.render.renderEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.flamingOctoIronman.subsystem.resource.BufferBuilder;

public class Primitive extends RenderEntity3D {
	private final float[] buffer;
	private final int indexCount;
	private final int GL_PRIMITIVE_TYPE;
	private static int VAO;
	private final ColorMethod method;
	private static int colorTypeUniform;
	private String materialFile;
	private String materialName;
	
	public Primitive(float[] buffer, int indexCount, int GL_PRIMITIVE_TYPE, ColorMethod method) {
		super(RenderEntity3D.createVBO(BufferBuilder.createFloatBuffer(buffer), GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW));
		this.buffer = buffer;
		this.indexCount = indexCount;
		this.GL_PRIMITIVE_TYPE = GL_PRIMITIVE_TYPE;
		this.method = method;
	}
	
	public Primitive(float[] buffer, int indexCount, int GL_PRIMITIVE_TYPE, ColorMethod method, String materialFile, String materialName){
		this(buffer, indexCount, GL_PRIMITIVE_TYPE, method);
		this.setMaterialFile(materialFile);
		this.setMaterialName(materialName);
	}
	
	@Override
	public Primitive clone(){
		return new Primitive(this.buffer, this.indexCount, this.GL_PRIMITIVE_TYPE, this.method);
	}
	
	@Override
	public void renderObject() {
		GL20.glUniform1i(colorTypeUniform, method.getColorType());
		//Prepare the VBO for drawing
		bindVBO();
		//Enable attributes
		GL20.glEnableVertexAttribArray(Attribute.POSITION.getLocation());	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(method.getLocation());	//Enable the attribute at location = 1 (color attribute)
		GL20.glEnableVertexAttribArray(Attribute.NORMAL.getLocation());	//Enable the normal attribute
		//Set attribute information
		GL20.glVertexAttribPointer(Attribute.POSITION.getLocation(), Attribute.POSITION.getSize(), GL11.GL_FLOAT, false, 0, indexCount * 0 * 4);	//Attribute for vertex position 
		GL20.glVertexAttribPointer(method.getLocation(), method.getSize(), GL11.GL_FLOAT, false, 0, indexCount * 0 * 4 + indexCount * Attribute.POSITION.getSize() * 4);	//Attribute for color position, offset of number of vertices * 4 (xyzw)
		GL20.glVertexAttribPointer(Attribute.NORMAL.getLocation(), Attribute.NORMAL.getSize(), GL11.GL_FLOAT, false, 0, indexCount * 0 * 4 + indexCount * Attribute.POSITION.getSize() * 4 + indexCount * method.getSize() * 4);
		
		//Draw the triangles
		GL11.glDrawArrays(GL_PRIMITIVE_TYPE, 0, indexCount);
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(Attribute.POSITION.getLocation());
		GL20.glDisableVertexAttribArray(method.getLocation());
		GL20.glDisableVertexAttribArray(Attribute.NORMAL.getLocation());
		
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

	public String getMaterialFile() {
		return materialFile;
	}

	public void setMaterialFile(String materialFile) {
		this.materialFile = materialFile;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}
