package com.flamingOctoIronman.subsystem.render;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class Material {
	private static int program;
	private static int material_shineUniform;
	private static int material_specularColorUniform;
	private Vector3f specularColor;
	private Vector3f diffuseColor;
	private Vector3f ambientColor;
	private float specularExponent;
	private int illuminationMethod;
	private String ambientMap;
	private String diffuseMap;
	
	public void loadMaterial(){
		GL20.glUniform1f(material_shineUniform, this.getSpecularExponent());
		GL20.glUniform3f(material_specularColorUniform, this.getSpecularColor().x, this.getSpecularColor().y, this.getSpecularColor().z);
	}

	public Vector3f getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Vector3f diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Vector3f getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Vector3f ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Vector3f getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Vector3f specularColor) {
		this.specularColor = specularColor;
	}

	public float getSpecularExponent() {
		return specularExponent;
	}

	public void setSpecularExponent(float specularExponent) {
		this.specularExponent = specularExponent;
	}

	public int getIlluminationMethod() {
		return illuminationMethod;
	}

	public void setIlluminationMethod(int illuminationMethod) {
		this.illuminationMethod = illuminationMethod;
	}

	public String getAmbientMap() {
		return ambientMap;
	}

	public void setAmbientMap(String ambientMap) {
		this.ambientMap = ambientMap;
	}

	public String getDiffuseMap() {
		return diffuseMap;
	}

	public void setDiffuseMap(String diffuseMap) {
		this.diffuseMap = diffuseMap;
	}
	
	public static void setProgram(int program){
		Material.program = program;
		material_shineUniform = GL20.glGetUniformLocation(program, "material.shine");
		material_specularColorUniform = GL20.glGetUniformLocation(program, "material.specularColor");
	}
}
