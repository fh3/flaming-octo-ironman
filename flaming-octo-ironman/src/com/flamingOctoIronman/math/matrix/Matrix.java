package com.flamingOctoIronman.math.matrix;

import info.yeppp.Core;


//TODO make this class more efficient, somehow implement generics
public class Matrix {
	/*
	 * Class information:
	 * This class is used to store and handle matrix operations
	 * It stores the actual matrix values in a two dimensional floating point array
	 */
	private float[][] matrix;
	
	public Matrix(int rows, int columns){
		this.matrix = new float[rows][columns];
	}
	public Matrix(float[][] matrix){
		this.matrix = matrix;
	}
	public Matrix(int rows, int columns, Matrix m){
		this.matrix = m.getMatrix();
		this.matrix[rows][columns] = this.matrix[rows][columns];
	}
	
	//Methods
	//Public Methods
	
	//Formats the Matrix into semi-readable text
	@Override
	public String toString(){
		String s = "";
		s += "[ ";
		for(float[] column : this.matrix){
			s+= "[ ";
			for(Float value : column){
				s += value.toString() + ", ";
			}
			s += "], ";
		}
		s += "]";
		return s;
	}
	
	//Get and set methods
	public void setMatrix(float[][] m){
		this.matrix = m;
	}
	public float[][] getMatrix(){
		return this.matrix;
	}
	public void setMatrixValue(int row, int column, float value){
		this.matrix[--row][--column] = value;
	}	
	public float getMatrixValue(int row, int column){
		return this.matrix[--row][--column];
	}
	public void setMatrixRow(int rowNumber, float[] row){
		if(this.matrix[--rowNumber].length == row.length){
			this.matrix[--rowNumber] = row;
		} else{
			System.out.println("Row of wrong size");
		}
	}
	public float[] getMatrixRow(int row){
		return this.matrix[--row];
	}
	//TODO might do something with a transposed matrix here
	public void setMatrixColumn(int columnNumber, float[] column){
		if(this.getColumns() == column.length){
			for(int i = 0; i < column.length; i++){
				System.out.println(columnNumber);
				this.getMatrixRow(i)[columnNumber] = column[i];
			}
		} else{
			System.out.println("Column of wrong size");
		}
	}
	public float[] getMatrixColumn(int columnNumber){
		float[] column = new float[this.matrix.length];
		--columnNumber;
		for(int i = 0; i < this.matrix.length; i++){
			column[i] = this.getMatrixRow(i + 1)[columnNumber];
		}
		return column;
	}
	public int getRows(){
		return this.matrix.length;
	}
	public int getColumns(){
		return this.matrix[0].length;
	}
	
	//Matrix calculations
	public void scalarMultiplication(float scalar){
		for(int row = 0; row < this.getRows(); row++){
			Core.Multiply_IV32fS32f_IV32f(this.matrix[row], 0, scalar, this.getColumns());
			this.setMatrixRow(row, this.matrix[row]);
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
