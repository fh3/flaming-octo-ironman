package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class MTLLoader {
	public static HashMap<String, Material> loadMaterial(File toLoad){
		String[] lines = ResourceManager.ReadFile(toLoad).split("\n");
		String[][] materialData = new String[lines.length][];
		int materialCount = 0;
		HashMap<String, Material> materialList;
		
		for(int i = 0; i < lines.length; i++){
			materialData[i] = lines[i].split(" ");
		}
		
		for(int i = 0; i < materialData.length; i++){
			if(materialData[i][0].equals("#") && materialData[i][2].equals("Material") && materialData[i][3].equals("Count:")){
				materialCount = Integer.parseInt(materialData[i][4]);
				i = materialData.length;	//Break out of the for loop
			}
			if(materialData[i][0].equals("newmtl")){
				materialCount++;
			}
		}
		
		materialList = new HashMap<String, Material>(materialCount);
		
		Material currentMaterial = null;
		for(int i = 0; i < materialData.length; i++){
			switch(materialData[i][0]){
				case "newmtl":
					materialList.put(materialData[i][1], new Material());
					currentMaterial = materialList.get(materialData[i][1]);
					break;
				case "Ka":
					currentMaterial.setAmbientColor(newVector3f(materialData[i][1], materialData[i][2], materialData[i][3]));
					break;
				case "Kd":
					currentMaterial.setDiffuseColor(newVector3f(materialData[i][1], materialData[i][2], materialData[i][3]));
					break;
				case "Ks":
					currentMaterial.setSpecularColor(newVector3f(materialData[i][1], materialData[i][2], materialData[i][3]));
					break;
				case "Ns":
					currentMaterial.setSpecularExponent(Float.parseFloat(materialData[i][1]));
					break;
				case "illum":
					currentMaterial.setIlluminationMethod(Integer.parseInt(materialData[i][1]));
					break;
				case "map_Ka":
					String s1[] = materialData[i][1].split("\\\\");
					currentMaterial.setAmbientMap(s1[s1.length - 1]);
					break;
				case "map_Kd":
					String s2[] = materialData[i][1].split("\\\\");
					currentMaterial.setDiffuseMap(s2[s2.length - 1]);
					break;
			}
		}
		
		return materialList;
	}
	
	private static Vector3f newVector3f(float x, float y, float z){
		Vector3f toReturn = new Vector3f();
		toReturn.x = x;
		toReturn.y = y;
		toReturn.z = z;
		return toReturn;
	}
	
	private static Vector3f newVector3f(String x, String y, String z){
		return newVector3f(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z));
	}
}
