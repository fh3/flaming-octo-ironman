package com.flamingOctoIronman.subsystem.render;

public class VertexArrayObject {
	private int[] usedList;
	private int address;
	
	public VertexArrayObject(int address){
		this.address = address;
		this.usedList = new int[16];
	}
	
	public int findEmptyVBO(){
		for(int i = 0; i < usedList.length; i++){
			if(usedList[i] != (Integer) null){
				return i;
			}
		}
		return -1;
	}
	
	public int getAddress(){
		return this.address;
	}
	
	public int[] getUsedList(){
		return usedList;
	}
}
