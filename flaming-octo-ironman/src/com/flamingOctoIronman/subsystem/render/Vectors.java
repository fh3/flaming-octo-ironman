package com.flamingOctoIronman.subsystem.render;

import org.lwjgl.util.vector.Vector3f;

/**
 * Instead of calculating the vectors every loop I have all of the calculated here
 * @author Quint Heinecke
 *
 */
public enum Vectors {
	i (RenderEngine2.createVector(1, 0, 0)),
	j (RenderEngine2.createVector(0, 1, 0)),
	k (RenderEngine2.createVector(0, 0, 1));
	
	private final Vector3f vector;
	private final float magnitude = 1;
	
	Vectors(Vector3f vector){
		this.vector = vector;
	}
	
	public Vector3f vector(){
		return this.vector;
	}
	public float magnitude(){
		return this.magnitude;
	}
}
