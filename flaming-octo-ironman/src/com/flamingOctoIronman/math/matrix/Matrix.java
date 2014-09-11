package com.flamingOctoIronman.math.matrix;


//TODO make this class more efficient, somehow implement generics
public class Matrix {
	private float[][] matrix;
	
	public Matrix(int rows, int columns){
		this.matrix = new float[columns][rows];
	}
	public Matrix(int rows, int columns, float[][] matrix){
		this.matrix = matrix;
		this.matrix[rows][columns] = this.matrix[rows][columns];	//This is done to make sure the array is initialized at a given position
	}
	public Matrix(int rows, int columns, Matrix m){
		this.matrix = m.getMatrix();
		this.matrix[rows][columns] = this.matrix[rows][columns];
	}
	
	public void setMatrix(float[][] m){
		this.matrix = m;
	}
	public float[][] getMatrix(){
		return this.matrix;
	}
	public void setMatrixValue(int row, int column, float value){
		this.matrix[row][column] = value;
	}
	
	public float getMatrixValue(int row, int column){
		return this.matrix[row][column];
	}

}
