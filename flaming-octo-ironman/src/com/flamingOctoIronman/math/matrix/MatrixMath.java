package com.flamingOctoIronman.math.matrix;

import java.text.DecimalFormat;

import info.yeppp.Core;

public class MatrixMath {
	/*
	 * Class information:
	 * This class is 
	 * used to perform math where the result is a new matrix
	 * or where two or more matrices are being operated on
	 * 
	 * All classes are to be static
	 */
	public static Matrix multipyMatrices(Matrix a, Matrix b){
		//Multiples matrices so product P = AB
		
		//This makes sure the matrices are of sizes that can be multiplied together
		if(a.getColumns() == b.getRows()){
			float[][] p = new float[a.getRows()][b.getColumns()];
			for(int row = 1; row <= a.getRows(); row++){
				for(int column = 1; column <= b.getColumns(); column++){
					p[row - 1][column - 1] = Core.DotProduct_V32fV32f_S32f(a.getMatrixRow(row), 0, b.getMatrixColumn(column), 0, b.getRows());
				}
			}
			return new Matrix(p);
		}else{
			System.out.println("Resultant matrix not defined");
			return null;
		}
	}
	public static Matrix addMatrices(Matrix a, Matrix b){
		if(a.getRows() == b.getRows() && a.getColumns() == b.getColumns()){
			float[][] matrixArray = new float[a.getRows()][a.getColumns()];
			for(int row = 0; row < a.getRows(); row++){
				for(int column = 0; column < a.getColumns(); column++){
					matrixArray[row][column] = a.getMatrixValue(row, column) + b.getMatrixValue(row, column);
				}
			}
			return new Matrix(matrixArray);
		} else{
			System.out.println("Matrices not of equal sides");
			return null;
		}
	}
	public static Matrix gaussianElimination(Matrix m){
		Matrix gaussianMatrix = new Matrix(m);	//Creates a new matrix so original isn't accidentally operated on
		//Loops through each column
		for(int column = 1; column < gaussianMatrix.getColumns(); column++){
			//Loops through each row
			for(int row = column; row < gaussianMatrix.getRows(); row++){
				//Checks to see if the given row has a leading coefficient other than one
				if(gaussianMatrix.getMatrixValue(row + 1, column) != 0){
					//If it does do row operation 3 with a calculated scalar
					RowOp3(gaussianMatrix, row + 1, column, (-1 * (gaussianMatrix.getMatrixValue(row + 1, column) / gaussianMatrix.getMatrixValue(column, column))));
				}
			}
		}
		return gaussianMatrix;
	}
	//Private methods for Gaussian Elimination
	//Swaps two rows in a matrix
	private void RowOp1(Matrix m, int row1, int row2){
		float[] buffer = m.getMatrixRow(row1);
		m.setMatrixRow(row1, m.getMatrixRow(row2));
		m.setMatrixRow(row2, buffer);;
	}
	//Multiplies a row in a matrix by a scalar (only one row, not the whole matrix)
	private static void RowOp2(Matrix m, int row, float scalar){
		Core.Multiply_IV32fS32f_IV32f(m.getMatrixRow(row), 0, scalar, m.getRows());
	}
	/** Implements the equation row1 = row1 + scalar * row2 in Matrix m
	 * based on DecimalFormat precision
	 * **/
	private static void RowOp3(Matrix m, int row1, int row2, float scalar){
		float[] multiplied = new float[m.getColumns()];	//Create an array for results
		Core.Multiply_V32fS32f_V32f(m.getMatrixRow(row2), 0, scalar, multiplied, 0, multiplied.length);	//Multiply by the scalar
		Core.Add_IV32fV32f_IV32f(multiplied, 0, m.getMatrixRow(row1), 0, multiplied.length);
		//Round values so math works out better
		for(int i = 0; i < multiplied.length; i++){
			multiplied[i] = Float.valueOf(Matrix.getDecimalFormat().format(multiplied[i]));
		}
		m.setMatrixRow(row1, multiplied);
	}
}