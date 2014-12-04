package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.util.ArrayList;

import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJLoader {
	public static float[] loadOBJ(File toLoad){
		String objString = ResourceManager.ReadFile(toLoad);
		String[] lines = objString.split("\n");
		String[][] objData = new String[lines.length][]; 
		ArrayList<Float> verticies = new ArrayList<Float>();
		float[] returnArray;
		for(int i = 0; i < lines.length; i++){
			objData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objData.length; i++){
			if(objData[i][0].equals("v")){
				verticies.add(Float.parseFloat(objData[i][1]));
				verticies.add(Float.parseFloat(objData[i][2]));
				verticies.add(Float.parseFloat(objData[i][3]));
				verticies.add(1.0f);					
			}
		}		
		for(int i = verticies.size(); i < verticies.size(); i++){
			verticies.add(1.0f);
		}
		returnArray = new float[verticies.size() * 2];
		for(int i = 0; i < verticies.size(); i++){
			returnArray[i] = verticies.get(i);
		}
		return returnArray;
	}
}
