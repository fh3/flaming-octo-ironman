package com.flamingOctoIronman.math.vector;

public class VectorCalculations {
	/* 
	 * Class information
	 * This class is used to perform math using vectors. Most methods will return either Vector objects
	 * or scalar values.
	 * 
	 * This is only used for methods that require two vectors
	 */
	
	//Methods
	//Public methods	
	public Vector vectorAddition(Vector a, Vector b){
		if(a.isDirectional() && b.isDirectional()){
			//Two directional vectors always equals a directional vector 
			return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ(), true);
		}else{
			//A directional vector and a point vector always equals a point
			return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ(), true);
		}
	}
	
	public Vector vectorSubtraction(Vector a, Vector b){
		//Vectors being subtracted always equal a directional vector regardless of type 
		//Vector "b" gets subtracted from vector "a"
		return new Vector(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ(), true);
	}	
	public float findDotProduct(Vector a, Vector b){
		return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
	}
	
	public float findAngle(Vector a, Vector b){
		a.updateMagnitude();
		b.updateMagnitude();
		return (float) Math.acos(findDotProduct(a, b) / (a.getMagnitude() * b.getMagnitude()));
	}
	
	public Vector findCrossProduct(Vector a, Vector b){
		return new Vector(a.getY() * b.getZ() - a.getZ() * b.getY(), a.getZ() * b.getX() - a.getX() * b.getZ(), a.getX() * b.getY() - a.getY() * b.getX(), true);
	}
	
	//Calculates the position vector of a point moving between two other vectors
	public Vector LERP(Vector a, Vector b, float percent){
		float change = 1 - percent;
		if(percent <= 1){
			return new Vector(change * a.getX() + percent * b.getX(), change * a.getY() + percent * b.getY(), change * a.getZ() + percent * b.getZ(), false);
		}
		else{
			return null;
		}
	}
}