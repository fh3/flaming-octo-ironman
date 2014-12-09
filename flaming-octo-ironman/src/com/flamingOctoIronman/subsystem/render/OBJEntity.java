package com.flamingOctoIronman.subsystem.render;

import java.io.File;

import com.flamingOctoIronman.subsystem.render.primitives.Primitive;
import com.flamingOctoIronman.subsystem.render.primitives.Texture;

public class OBJEntity {
	private Primitive objectPrimitive;
	private Texture objectTexture;
	
	public OBJEntity(File objectFile, File textureFile) {
		objectPrimitive = OBJLoader.loadObject(objectFile);
		objectTexture = BMPLoader.loadBMP(textureFile);
	}
	
	public Primitive getPrimitive(){
		return objectPrimitive;
	}
	
	public Texture getTexture(){
		return objectTexture;
	}

}
