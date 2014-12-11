package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.flamingOctoIronman.subsystem.render.primitives.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJEntity {
	private Primitive objectPrimitive;
	private Texture objectTexture;
	
	public OBJEntity(File objectFile, File textureFile) {
		objectPrimitive = OBJLoader.loadObject(objectFile);
		try {
			objectTexture = TextureLoader.getTexture("BMP", ResourceManager.getFileInputStream(textureFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Primitive getPrimitive(){
		return objectPrimitive;
	}
	
	public Texture getTexture(){
		return objectTexture;
	}

}
