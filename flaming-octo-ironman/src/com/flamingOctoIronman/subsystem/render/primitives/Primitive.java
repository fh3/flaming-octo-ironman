package com.flamingOctoIronman.subsystem.render.primitives;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.flamingOctoIronman.subsystem.render.RenderEngine2;

public class Primitive extends VisualObject {
	private final int GL_RENDER_TYPE;
	private final int verticiesLength;
	private final int GL_ARRAY_TYPE;
	
	public Primitive(float[] buffer, int GL_ARRAY_TYPE, int GL_RENDER_TYPE) {
		super(VisualObject.createVBO(RenderEngine2.createFloatBuffer(buffer), GL_ARRAY_TYPE, GL15.GL_STATIC_DRAW));
		this.GL_RENDER_TYPE = GL_RENDER_TYPE;
		this.verticiesLength = buffer.length;
		this.GL_ARRAY_TYPE = GL_ARRAY_TYPE;
	}
	@Override
	public void renderObject() {
		//Prepare the VBO for drawing
		GL15.glBindBuffer(GL_ARRAY_TYPE, this.getVBO());	//Bind the VBO
		//Enable attributes
		GL20.glEnableVertexAttribArray(0);	//Enable the attribute at location = 0 (position attribute)
		GL20.glEnableVertexAttribArray(1);	//Enable the attribute at location = 1 (color attribute)
		//Set attribute information
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);	//Attrib 0 is a vec4
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, verticiesLength * 2);	//Attrib 1 is a vec4, offset of data.length * 2
		
		//Draw the triangles
		GL11.glDrawArrays(GL_RENDER_TYPE, 0, verticiesLength / 8);	//The type of primitive, the first position in the array, the number of positions to draw 
		
		//Disable the attributes
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		//Unbind the buffer
		GL15.glBindBuffer(GL_ARRAY_TYPE, 0);		
	}
}
