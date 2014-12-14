package com.flamingOctoIronman.subsystem.render.renderEntity;

public enum ColorMethod {
	TEXTURE(2, 1, 0),
	COLOR(3, 2, 1);
	
	private final int size;
	private final int location;
	private final int colorType;
	
	ColorMethod(int size, int location, int colorType){
		this.size = size;
		this.location = location;
		this.colorType = colorType;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getLocation(){
		return this.location;
	}
	
	public int getColorType(){
		return this.colorType;
	}
}
