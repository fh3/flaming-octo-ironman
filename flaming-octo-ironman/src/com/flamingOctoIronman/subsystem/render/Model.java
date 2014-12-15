package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.flamingOctoIronman.subsystem.render.renderEntity.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class Model {
	private Primitive objectPrimitive;
	private Texture objectTexture;
	
	public Model(File objectFile, File textureFile) {
		objectPrimitive = OBJLoader.loadObject(objectFile);
		try {
			objectTexture = TextureLoader.getTexture(textureFile.getAbsolutePath().split("\\.(?=[^\\.]+$)")[1], ResourceManager.getFileInputStream(textureFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		objectTexture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
	    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}
	
	public Primitive getPrimitive(){
		return objectPrimitive;
	}
	
	public Texture getTexture(){
		return objectTexture;
	}

}
