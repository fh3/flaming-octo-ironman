/**
 * 
 */
package com.flamingOctoIronman.math.matrix;

/**
 * @author fh3
 *
 */
public class IdentityMatrix extends Matrix{
	/*
	 * Class information:
	 * This class is used to and fill create identity matrices
	 */
	
	//Common identity matrices should probably be initialized at the start of some main module with static final modifiers
	public IdentityMatrix(int size) {
		super(size, size);
		//Sets value to 0 if the row isn't the same as the column
		for(int row = 1; row < size; row++){
			for(int column = 1; column < size; column++){
				if(row == column){
					super.setMatrixValue(row, column, 1);
				} else{
					super.setMatrixValue(row, column, 0);
				}
			}
		}
	}
}
