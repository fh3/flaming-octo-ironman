package com.flamingOctoIronman.math.matrix;

import info.yeppp.Core;

public class MatrixMath {
	/*
	 * Class information:
	 * This class is used to perform math where the result is a new matrix
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
}
