/**
 * 
 */
package com.flamingOctoIronman.math.matrix;

/**
 * @author fh3
 *
 */
public class IdentityMatrix extends Matrix{
	//Common identity matrices should probably be initialized at the start of some main module with static final modifiers
	public IdentityMatrix(int size) {
		super(size, size);
		//Sets value to 0 if the row isn't the same as the column
		for(int row = 0; row < size; row++){
			for(int column = 0; column < size; column++){
				if(row == column){
					super.setMatrixValue(row, column, 0);
				} else{
					super.setMatrixValue(row, column, 1);
				}
			}
		}
	}
}
