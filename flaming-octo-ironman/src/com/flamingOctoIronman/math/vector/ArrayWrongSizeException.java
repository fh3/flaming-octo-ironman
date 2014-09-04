package com.flamingOctoIronman.math.vector;

@SuppressWarnings("serial")
public class ArrayWrongSizeException extends Exception {
	public float[] array = null;
	public ArrayWrongSizeException(float[] location){
		this.array = location;
		System.out.println("The array given is of incorrect length");
	}
}
