package com.flamingOctoIronman.math.vector;

import info.yeppp.Core;

public class Vector {
	/* Class information
	 * This class is used to define vectors. Floating point numbers are used to allow vectors to exist between
	 * points on the scene graph. This class can be used for both positional and directional vectors.	
	 */
	//Variables
	private float location[];		//This is the value of the vector
	private boolean isDirectional;	//This is the vector type, either directional or positional
	private float magnitude;
	
	//Constructors
	public Vector(boolean isDirectional){			//Constructor chaining is used here to provide a number of options
		this(0.0f, 0.0f, 0.0f, isDirectional);		//when creating vectors
	}

	public Vector(float location[], boolean isDirectional){
		this.location = new float[3];
		this.location = location;
		this.isDirectional = isDirectional;
	}
	
	public Vector(float x, float y, float z, boolean isDirectional){
		this.location[0] = x;
		this.location[1] = y;
		this.location[2] = z;
		this.isDirectional = isDirectional;
		this.updateMagnitude();
	}
	
	//Methods
	
	//Get and set methods
	public float getX(){
		return this.location[0];
	}
	
	public void setX(float x){
		this.location[0] = x;
		this.updateMagnitude();
	}
	
	public float getY(){
		return this.location[1];
	}
	
	public void setY(float y){
		this.location[1] = y;
		this.updateMagnitude();
	}
	
	public float getZ(){
		return this.location[2];
	}
	
	public void setZ(float z){
		this.location[2] = z;
		this.updateMagnitude();
	}
	
	public boolean isDirectional(){
		return this.isDirectional;
	}
	
	public float getMagnitude(){
		return this.magnitude;
	}
	public float[] getArray(){
		return this.location;
	}
	
	//Methods for Vector calculations
		
	//Public methods
	public void scalarMultiplication(float scalar){
		this.location[0] *= scalar;
		this.location[1] *= scalar;
		this.location[2] *= scalar;
	}
	
	public void scalarDivision(float scalar){
		this.location[0] /= scalar;
		this.location[1] /= scalar;
		this.location[2] /= scalar;
	}
	
	@Override
	public String toString(){
		return "<" + location[0] + ", " + location[1] + ", " + location[2] + ">";
	}
	
	//This function is computationally expensive, use conservatively
	private void updateMagnitude(){
		magnitude = (float) Math.sqrt(vectorSumSquared());
	}
	
	//Private methods
	private float vectorSumSquared(){
		return Core.SumSquares_V32f_S32f(location, 0, location.length);
	}
}
