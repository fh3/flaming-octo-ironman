package com.flamingOctoIronman;

public class Main {
	//All this class does is launch and create a new game instance
	public static void main(String args[]){
		FlamingOctoIronman game = FlamingOctoIronman.getInstance();
		game.startGame();
	}
}
