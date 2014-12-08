package com.flamingOctoIronman.subsystem.render;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.flamingOctoIronman.subsystem.render.primitives.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJLoader {
	public static float[] loadVerticies(String toLoad){
		String[] lines = toLoad.split("\n");
		String[][] objData = new String[lines.length][]; 
		float[] verticies;
		float[] uvVerticies;
		int vertexCount = 0;
		int uvCount = 0;
		boolean usesMTL = false;
		//ArrayList<Float> verticies = new ArrayList<Float>();
		for(int i = 0; i < lines.length; i++){
			objData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objData.length; i++){
			if(objData[i][0].equals("v")){
				vertexCount += 4;					
			}
			if(objData[i][0].equals("mtllib")){
				usesMTL = true;
			}
			if(objData[i][0].equals("vt")){
				uvCount += 2;					
			}
		}
		verticies = new float[vertexCount];
		uvVerticies = new float[uvCount];
		int next = 0;
		if(usesMTL){
			
		} else{
			for(int i = 0; i < objData.length; i++){
				if(objData[i][0].equals("v")){
					verticies[next++] = Float.parseFloat(objData[i][1]);
					verticies[next++] = Float.parseFloat(objData[i][2]);
					verticies[next++] = Float.parseFloat(objData[i][3]);
					verticies[next++] = 1.0f;					
				}
			}
		}
		return verticies;
	}
	
	public static int[] loadIndicies(String toLoad){
		String[] lines = toLoad.split("\n");
		String[][] objData = new String[lines.length][];
		int[] indicies;
		int count = 0;
		for(int i = 0; i < lines.length; i++){
			objData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objData.length; i++){
			if(objData[i][0].equals("f")){
				count += 3;
			}
		}
		indicies = new int[count];
		int next = 0;
		for(int i = 0; i < objData.length; i++){
			if(objData[i][0].equals("f")){
				indicies[next++] = Integer.parseInt(objData[i][1]);			
				indicies[next++] = Integer.parseInt(objData[i][2]);	
				indicies[next++] = Integer.parseInt(objData[i][3]);
			}
		}
		return indicies;
	}
	
	public static Primitive loadObject(File toLoad){
		String objectString = ResourceManager.ReadFile(toLoad);
		float[] verticies = loadVerticies(objectString);
		int[] indicies = loadIndicies(objectString);
		float[] objectBuffer = new float[indicies.length * 4 * 2];
		
		for(int i = 0; i < indicies.length; i++){
			objectBuffer[(i * 4) + 0] = verticies[(indicies[i] - 1) * 4 + 0];
			objectBuffer[(i * 4) + 1] = -verticies[(indicies[i] - 1) * 4 + 2];
			objectBuffer[(i * 4) + 2] = verticies[(indicies[i] - 1) * 4 + 1];
			objectBuffer[(i * 4) + 3] = 1.0f;
		}
		
		for(int i = objectBuffer.length / 2; i < objectBuffer.length; i++){
			objectBuffer[i] = 0.5f;
		}
		return new Primitive(objectBuffer, GL11.GL_TRIANGLES);
	}
}
