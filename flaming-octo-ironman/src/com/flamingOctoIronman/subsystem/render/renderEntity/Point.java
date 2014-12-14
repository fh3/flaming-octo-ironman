package com.flamingOctoIronman.subsystem.render.renderEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Point extends Primitive{

	public Point(Vector3f point, Vector3f color, float size) {
		super(new float[]{	point.x, 
							point.y, 
							point.z,
							
							color.x,
							color.y,
							color.z}, 1, GL11.GL_POINTS, ColorMethod.COLOR);
		GL11.glPointSize(size);
	}

}
