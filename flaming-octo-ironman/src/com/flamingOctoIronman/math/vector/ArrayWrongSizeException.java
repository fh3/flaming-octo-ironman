package com.flamingOctoIronman.math.vector;

public class ArrayWrongSizeException extends Exception {
	public float[] array = null;
	public ArrayWrongSizeException(float[] location){
		this.array = location;
		System.out.println("The array given is of incorrect length");
	}
}
