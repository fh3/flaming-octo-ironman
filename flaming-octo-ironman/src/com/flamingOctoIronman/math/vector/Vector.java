package com.flamingOctoIronman.math.vector;

public class Vector {
	/* Class information
	 * This class is used to define vectors. Floating point numbers are used to allow vectors to exist between
	 * points on the scene graph. This class can be used for both positional and directional vectors.	
	 */
	//Variables
	private float location[] = new float[3];		//This is the value of the vector
	private boolean isDirectional = false;			//This is the vector type, either directional or positional
	
	//Constructors
	public Vector(boolean isDirectional){			//Constructor chaining is used here to provide a number of options
		this(0.0f, 0.0f, 0.0f, isDirectional);		//when creating vectors
	}

	public Vector(float location[], boolean isDirectional) throws ArrayWrongSizeException{
		if(location.length == 3){
			this.location = location;
			this.isDirectional = isDirectional;
		} else{
			throw new ArrayWrongSizeException(location);		//This is to prevent an array larger than three
		}														//From being accidentally passed as the vector
	}
	
	public Vector(float x, float y, float z, boolean isDirectional){
		this.location[0] = x;
		this.location[1] = y;
		this.location[2] = z;
		this.isDirectional = isDirectional;
	}
	
	//Methods
	
	//Get and set methods
	public float getX(){
		return this.location[0];
	}
	
	public void setX(float x){
		this.location[0] = x;
	}
	
	public float getY(){
		return this.location[1];
	}
	
	public void setY(float y){
		this.location[1] = y;
	}
	
	public float getZ(){
		return this.location[2];
	}
	
	public void setZ(float z){
		this.location[2] = z;
	}
	
	public boolean isDirectional(){
		return this.isDirectional;
	}
}
