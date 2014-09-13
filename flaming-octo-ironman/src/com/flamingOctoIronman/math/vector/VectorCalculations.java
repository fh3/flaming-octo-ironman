package com.flamingOctoIronman.math.vector;

import info.yeppp.Core;

public class VectorCalculations {
	/* 
	 * Class information
	 * This class is used to perform math using vectors. Most methods will return either Vector objects
	 * or scalar values.
	 * 
	 * This is only used for methods that require two vectors
	 * 
	 * Update: Now using Yeppp! library to use SIMD instructions, greatly decreasing computation time
	 */
	
	//Methods
	//Public methods	
	public static Vector vectorAddition(Vector a, Vector b){
		float[] sum = {0, 0, 0};
		if(a.isDirectional() && b.isDirectional()){
			//Two directional vectors always equals a directional vector 
			Core.Add_V32fV32f_V32f(a.getArray(), 0, b.getArray(), 0, sum, 0, 3);
			return new Vector(sum, true);
		}else{
			//A directional vector and a point vector always equals a point
			Core.Add_V32fV32f_V32f(a.getArray(), 0, b.getArray(), 0, sum, 0, 3);
			return new Vector(sum, false);
		}
	}
	
	public static Vector vectorSubtraction(Vector a, Vector b){
		//Vectors being subtracted always equal a directional vector regardless of type 
		//Vector "b" gets subtracted from vector "a"
		float[] difference = {0, 0, 0};
		Core.Subtract_V32fV32f_V32f(a.getArray(), 0, b.getArray(), 0, difference, 0, 3);
		return new Vector(difference, false);
	}	
	public static float findDotProduct(Vector a, Vector b){
		return Core.DotProduct_V32fV32f_S32f(a.getArray(), 0, b.getArray(), 0, 3);
	}
	
	public static float findAngle(Vector a, Vector b){
		return (float) Math.acos(findDotProduct(a, b) / (a.getMagnitude() * b.getMagnitude()));
	}
	
	public static Vector findCrossProduct(Vector a, Vector b){
		return new Vector(a.getY() * b.getZ() - a.getZ() * b.getY(), a.getZ() * b.getX() - a.getX() * b.getZ(), a.getX() * b.getY() - a.getY() * b.getX(), true);
	}
	
	//Calculates the position vector of a point moving between two other vectors
	public static Vector LERP(Vector a, Vector b, float percent){
		float change = 1 - percent;
		if(percent <= 1){
			float[] aResults = {0, 0, 0};
			float[] bResults = {0, 0, 0};
			Core.Multiply_V32fS32f_V32f(a.getArray(), 0, change, aResults, 0, 3);
			Core.Multiply_V32fS32f_V32f(b.getArray(), 0, percent, bResults, 0, 3);
			Core.Add_IV32fV32f_IV32f(aResults, 0, bResults, 0, 3);
			return new Vector(aResults, false);
		}
		else{
			return null;
		}
	}
}