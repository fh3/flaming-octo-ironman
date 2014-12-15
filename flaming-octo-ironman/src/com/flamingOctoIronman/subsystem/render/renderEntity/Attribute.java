package com.flamingOctoIronman.subsystem.render.renderEntity;

public enum Attribute {
	POSITION(0, 3), 
	TEXTURE(ColorMethod.TEXTURE.getLocation(), ColorMethod.TEXTURE.getSize()),
	COLOR(ColorMethod.COLOR.getLocation(), ColorMethod.COLOR.getSize()),
	NORMAL(3, 3);
	
	private final int location;
	private final int size;
	
	Attribute(int location, int size){
		this.location = location;
		this.size = size;
	}
	
	public int getLocation(){
		return this.location;
	}
	
	public int getSize(){
		return this.size;
	}
}
