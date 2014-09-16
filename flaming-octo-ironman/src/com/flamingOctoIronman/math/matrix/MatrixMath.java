package com.flamingOctoIronman.math.matrix;

import info.yeppp.Core;

public class MatrixMath {
	/*
	 * Class information:
	 * This class is 
	 * used to perform math where the result is a new matrix
	 * or where two or more matrices are being operated on
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
		//Checks to see if there are multiple leading coefficients 
		int coefficient = 0;
		int scalar;
		for(int row = 1; row < gaussianMatrix.getRows(); row++){
			if(gaussianMatrix.getMatrixRow(row)[0] != 0){
				coefficient = row;
			}
		}
		scalar = 
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
	//Implements the equation row1 = row1 + scalar * row2
	private static void RowOp3(Matrix m, int row1, int row2, float scalar){
		float[] multiplied = new float[m.getColumns()];	//Create an array for results
		Core.Multiply_V32fS32f_V32f(m.getMatrixRow(row2), 0, scalar, multiplied, 0, multiplied.length);	//Multiply by the scalar
		Core.Add_IV32fV32f_IV32f(m.getMatrixRow(row1), 0, multiplied, 0, multiplied.length);
	}
	//Checks to see if there are multiple leading coefficients
	private static int coefficientCheck(Matrix m, int column, int startingColumn){
		for(int row = 1; row < m.getRows(); row++){
			if(m.getMatrixRow(row)[column] != 0){
				return row;
			}
		}
		return 0;
	}
}