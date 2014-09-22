package com.flamingOctoIronman;

/**
 * This class starts the game. It may later be merged into the FlamingOctoIronman class.
 * @author Quint
 */
public class Main {
	/**
	 *  This method is called when the JVM starts. It starts the game by getting the <code>FlamingOctoIronman</code> 
	 *  and calling <code>startGame()</code>.
	 * @param args	A <code>String</code> of arguments passed to the game via command line, does not currently do anything
	 */
	public static void main(String args[]){
		FlamingOctoIronman game = FlamingOctoIronman.getInstance();
		game.startGame();
	}
}
