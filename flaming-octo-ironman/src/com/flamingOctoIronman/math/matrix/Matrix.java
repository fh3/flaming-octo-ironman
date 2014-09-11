package com.flamingOctoIronman.math.matrix;


//TODO make this class more efficient, somehow implement generics
public class Matrix {
	/*
	 * Class information:
	 * This class is used to store and handle matrix operations
	 * It stores the actual matrix values in a two dimensional floating point array
	 */
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
	
	//Methods
	//Public Methods
	//Get and set methods
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
	public int getRows(){
		return this.matrix.length;
	}
	public int getColumns(){
		return this.matrix[0].length;
	}
	
	//Matrix calculations
	public void scalarMultiplication(float scalar){
		for(int rows = 0; rows < this.getRows(); rows++){
			for(int columns = 0; columns < this.getColumns(); columns++){
				this.setMatrixValue(rows, columns, this.getMatrixValue(rows, columns) * scalar);
			}
		}
	}
	public void scalarDivision(float scalar){
		for(int rows = 0; rows < this.getRows(); rows++){
			for(int columns = 0; columns < this.getColumns(); columns++){
				this.setMatrixValue(rows, columns, this.getMatrixValue(rows, columns) / scalar);
			}
		}
	}

}
