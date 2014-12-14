package com.flamingOctoIronman.subsystem.render.renderEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Triangle extends Primitive {

	public Triangle(Vector3f pointOne, Vector3f pointTwo, Vector3f pointThree, Vector3f color) {
		super(new float[]{
				pointOne.x,
				pointOne.y,
				pointOne.z,
				
				pointTwo.x,
				pointTwo.y,
				pointTwo.z,
				
				pointThree.x,
				pointThree.y,
				pointThree.z,
				
				color.x,
				color.y,
				color.z,
				
				color.x,
				color.y,
				color.z,
				
				color.x,
				color.y,
				color.z	
		}, 3, GL11.GL_TRIANGLES, ColorMethod.COLOR);
	}

}
