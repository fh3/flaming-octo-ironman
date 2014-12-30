package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;

import com.flamingOctoIronman.subsystem.render.renderEntity.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class Model {
	private Primitive primitive;
	private Texture texture;
	private Material material;
	
	public Model(File primitive, File material, File texture){
		this.primitive = OBJLoader.loadObject(primitive);
		if(material == null){
			HashMap<String, Material> materialMap = MTLLoader.loadMaterial(new File(primitive.getParentFile().getAbsolutePath() + "\\" + this.primitive.getMaterialFile()));
			this.material = materialMap.get(this.primitive.getMaterialName());
		} else{
			HashMap<String, Material> materialMap = MTLLoader.loadMaterial(material);
			this.material = materialMap.get(this.primitive.getMaterialName());
		}
		if(texture == null){
			this.texture = loadTexture(new File(primitive.getParentFile().getAbsolutePath() + "\\" + this.material.getDiffuseMap()));
		} else{
			this.texture = loadTexture(texture);
		}
		
		setupTexture();
	}
	
	public Model(Primitive primitive, Material material, Texture texture){
		this.primitive = primitive;
		this.material = material;
		this.texture = texture;
		
		setupTexture();
	}
	
	public Model(File primitive){
		this(primitive, null, null);
	}
	
	@Override
	public Model clone(){
		return new Model(this.primitive, this.material, this.texture);
	}
	
	public Primitive getPrimitive(){
		return primitive;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public void setTexture(Texture texture){
		this.texture = texture;
	}

	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material){
		this.material = material;
	}
	
	public static Texture loadTexture(File texture){
		try {
			return TextureLoader.getTexture(texture.getAbsolutePath().split("\\.(?=[^\\.]+$)")[1], ResourceManager.getFileInputStream(texture));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void setupTexture(){
		this.texture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
	    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	    TextureImpl.bindNone();
	}
}
