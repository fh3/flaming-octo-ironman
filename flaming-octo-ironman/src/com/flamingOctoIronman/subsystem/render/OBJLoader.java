package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.flamingOctoIronman.subsystem.render.primitives.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJLoader {
	public static float[] loadVerticies(String toLoad){
		String[] lines = toLoad.split("\n");
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
		returnArray = new float[verticies.size() * 2];
		for(int i = 0; i < verticies.size(); i++){
			returnArray[i] = verticies.get(i);
		}
		for(int i = verticies.size(); i < verticies.size() * 2; i++){
			returnArray[i] = 1.0f;
		}
		return returnArray;
	}
	
	public static float[] loadIndicies(String toLoad){
		String[] lines = toLoad.split("\n");
		String[][] objData = new String[lines.length][];
		ArrayList<Float> indicies = new ArrayList<Float>();
		float[] returnArray;
		for(int i = 0; i < lines.length; i++){
			objData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objData.length; i++){
			if(objData[i][0].equals("f")){
				indicies.add(Float.parseFloat(objData[i][1]) - 1);			
				indicies.add(Float.parseFloat(objData[i][2]) - 1);	
				indicies.add(Float.parseFloat(objData[i][3]) - 1);	
				
				indicies.add(Float.parseFloat(objData[i][2]) - 1);	
				indicies.add(Float.parseFloat(objData[i][3]) - 1);	
				indicies.add(Float.parseFloat(objData[i][4]) - 1);	
			}
		}
		returnArray = new float[indicies.size()];
		for(int i = 0; i < indicies.size(); i++){
			returnArray[i] = indicies.get(i);
		}
		return returnArray;
	}
	
	public static Primitive loadObject(File toLoad){
		String objectString = ResourceManager.ReadFile(toLoad);
		float [] verticies = loadVerticies(objectString);
		float [] indices = loadIndicies(objectString);
		return new Primitive(verticies, indices, GL15.GL_ELEMENT_ARRAY_BUFFER, GL11.GL_TRIANGLES);
	}
}
