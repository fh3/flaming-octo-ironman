package com.flamingOctoIronman.subsystem.render.primitives;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.flamingOctoIronman.subsystem.render.RenderEngine2;

public class Primitive extends VisualObject {
	private final int GL_RENDER_TYPE;
	private final int verticiesLength;
	private final int GL_ARRAY_TYPE;
	private final int VBO_2;
	private static int VAO;
	
	public Primitive(float[] buffer, float[] indicies, int GL_ARRAY_TYPE, int GL_RENDER_TYPE) {
		super(VisualObject.createVBO(RenderEngine2.createFloatBuffer(buffer), GL_ARRAY_TYPE, GL15.GL_STATIC_DRAW));
		this.GL_RENDER_TYPE = GL_RENDER_TYPE;
		this.verticiesLength = buffer.length;
		this.GL_ARRAY_TYPE = GL_ARRAY_TYPE;
		if(indicies != null){
			VBO_2 = VisualObject.createVBO(RenderEngine2.createFloatBuffer(indicies), GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW);
		}else{
			VBO_2 = -1;
		}
	}
	@Override
	public void renderObject() {
		if(GL_ARRAY_TYPE == GL15.GL_ARRAY_BUFFER){
			renderArray();
		}
		if(GL_ARRAY_TYPE == GL15.GL_ELEMENT_ARRAY_BUFFER){
			renderElements();
		}
	}
	
	private void renderElements(){
		GL11.glDrawElements(GL11.GL_TRIANGLES, , type, indices);
	}
	
	public void initializeObjects(){
		GL30.glBindVertexArray(VAO);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, getVBO());
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attrib 0 is a vec4
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, verticiesLength * 2);	//Attrib 1 is a vec4, offset of data.length * 2
		if(VBO_2 != -2){
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBO_2);
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
	}
	
	private void renderArray(){
		//Prepare the VBO for drawing
		bindVBO_Verticies();
		
		//Draw the triangles
		GL11.glDrawArrays(GL_RENDER_TYPE, 0, verticiesLength / 8);	//The type of primitive, the first position in the array, the number of positions to draw 
				
		//Unbind the buffer
		unbindVBO();
	}
	
	public void bindVBO_Verticies(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getVBO());
	}
	
	public void unbindVBO(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void bindIndicies(){
		if(VBO_2 != -1){
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBO_2);
		}
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
