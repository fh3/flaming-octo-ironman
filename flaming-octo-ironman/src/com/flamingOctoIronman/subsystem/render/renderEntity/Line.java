package com.flamingOctoIronman.subsystem.render.renderEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Line extends Primitive {
	private float width;
	public Line(Vector3f pointOne, Vector3f pointTwo, Vector3f color, float width) {
		super(new float[]{
				pointOne.x,
				pointOne.y,
				pointOne.z,
				
				pointTwo.x,
				pointTwo.y,
				pointTwo.z,
				
				color.x,
				color.y,
				color.z,
				
				color.x,
				color.y,
				color.z
		}, 2, GL11.GL_LINES, ColorMethod.COLOR);
		
		this.width = width;
	}

	@Override
	public void renderObject(){
		GL11.glLineWidth(width);
		super.renderObject();
	}
}
