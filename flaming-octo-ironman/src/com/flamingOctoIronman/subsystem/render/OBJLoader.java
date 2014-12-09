package com.flamingOctoIronman.subsystem.render;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.flamingOctoIronman.subsystem.render.primitives.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJLoader {
	public static Primitive loadObject(File toLoad){
		String[] lines = ResourceManager.ReadFile(toLoad).split("\n");
		String[][] objectData = new String[lines.length][];
		int vertexCount = 0;
		int indexCount = 0;
		int uvCount = 0;
		boolean usesMTL = false;
		for(int i = 0; i < lines.length; i++){
			objectData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objectData.length; i++){
			if(objectData[i][0].equals("v")){
				vertexCount += 4;					
			}
			if(objectData[i][0].equals("f")){
				indexCount += 3;
				if(objectData[i][1].contains("/")){
					usesMTL = true;
				}
			}
			if(objectData[i][0].equals("vt")){
				uvCount += 2;					
			}
		}
		float[] verticies = new float[vertexCount];
		int[] vertexIndicies = new int[indexCount];
		int[] uvVertexIndicies = new int[indexCount];
		float[] uvVerticies = new float[uvCount];
		
		int verticiesNext = 0;
		int vertexIndiciesNext = 0;
		int uvVertexIndiciesNext = 0;
		int uvVerticiesNext = 0;
		for(int i = 0; i < objectData.length; i++){
			if(objectData[i][0].equals("v")){
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][1]);
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][2]);
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][3]);
				verticies[verticiesNext++] = 1.0f;					
			}
			
			if(objectData[i][0].equals("f")){
				if(usesMTL){
					String[] combinedIndicies;
					for(int j = 1; j < 4; j++){
						combinedIndicies = objectData[i][j].split("/");
						vertexIndicies[vertexIndiciesNext++] = Integer.parseInt(combinedIndicies[0]);
						uvVertexIndicies[uvVertexIndiciesNext++] = Integer.parseInt(combinedIndicies[1]);
					}
				} else{
					vertexIndicies[uvVertexIndiciesNext++] = Integer.parseInt(objectData[i][1]);			
					vertexIndicies[uvVertexIndiciesNext++] = Integer.parseInt(objectData[i][2]);	
					vertexIndicies[uvVertexIndiciesNext++] = Integer.parseInt(objectData[i][3]);
				}
			}
			
			if(usesMTL && objectData[i][0].equals("vt")){
				uvVerticies[uvVerticiesNext++] = Float.parseFloat(objectData[i][1]);
				uvVerticies[uvVerticiesNext++] = Float.parseFloat(objectData[i][2]);
			}
		}
		
		float[] objectBuffer;
		
		if(usesMTL){
			objectBuffer = new float[indexCount * 6];
		} else{
			objectBuffer = new float[indexCount * 4];
		}
		
		for(int i = 0; i < indexCount; i++){
			objectBuffer[(i * 4) + 0] = verticies[(vertexIndicies[i] - 1) * 4 + 0];
			objectBuffer[(i * 4) + 1] = -verticies[(vertexIndicies[i] - 1) * 4 + 2];
			objectBuffer[(i * 4) + 2] = verticies[(vertexIndicies[i] - 1) * 4 + 1];
			objectBuffer[(i * 4) + 3] = 1.0f;
		}
		
		if(usesMTL){
			for(int i = indexCount; i < indexCount * 2; i++){
				objectBuffer[(i * 2) + 0] = uvVerticies[(uvVertexIndicies[i / 2] - 1) * 2 + 0];
				objectBuffer[(i * 2) + 1] = uvVerticies[(uvVertexIndicies[i / 2] - 1) * 2 + 1];
				
			}
		}
		System.out.println("Diff: " + Integer.toString(objectBuffer.length - (uvVertexIndicies.length * 2 + vertexIndicies.length * 4)));
		return new Primitive(objectBuffer, GL11.GL_TRIANGLES);
	}
}
