package com.flamingOctoIronman.math.vector;

public class VectorCalculations {
	/* Class information
	 * This class is used to perform math using vectors. Most methods will return either Vector objects
	 * or scalar values.
	 */
	
	//Methods
	//Public methods
	public Vector scalarMultiplication(Vector vector, float scalar){
		return new Vector(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar, vector.isDirectional());
	}
	
	public Vector scalarDivision(Vector vector, float scalar){
		return new Vector(vector.getX() / scalar, vector.getY() / scalar, vector.getZ() / scalar, vector.isDirectional());
	}
	
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
	
	//Private methods
	private float vectorSumSquared(Vector v){
		return v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ();
	}
}
