package com.flamingOctoIronman.subsystem.render.primitives;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.flamingOctoIronman.subsystem.resource.BufferBuilder;

public class Texture {
	private byte[] data;
	private int size;
	private int width;
	private int height;
	private int textureID;
	private static int textureSamplerUniform = -1;
	
	public Texture(byte data[], int size, int width, int height){
		this.data = data;
		this.size = size;
		this.width = width;
		this.height = height;
		
		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL12.GL_BGR, GL11.GL_UNSIGNED_BYTE, BufferBuilder.createByteBuffer(data));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void glSelectTexture(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL20.glUniform1i(textureSamplerUniform, textureID);
	}
	
	public static void setTextureUniform(int textureSamplerUniform){
		Texture.textureSamplerUniform = textureSamplerUniform;
	}
}
