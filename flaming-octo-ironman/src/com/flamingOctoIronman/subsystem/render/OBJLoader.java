package com.flamingOctoIronman.subsystem.render;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.flamingOctoIronman.subsystem.render.renderEntity.ColorMethod;
import com.flamingOctoIronman.subsystem.render.renderEntity.Primitive;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class OBJLoader {
	public static Primitive loadObject(File toLoad){
		String[] lines = ResourceManager.ReadFile(toLoad).split("\n");
		String[][] objectData = new String[lines.length][];
		int vertexCount = 0;
		int indexCount = 0;
		int uvCount = 0;
		int normalCount = 0;
		boolean usesMTL = false;
		boolean usesNormals = false;
		for(int i = 0; i < lines.length; i++){
			objectData[i] = lines[i].split(" ");
		}
		for(int i = 0; i < objectData.length; i++){
			if(objectData[i][0].equals("v")){
				vertexCount += 3;					
			}
			if(objectData[i][0].equals("f")){
				indexCount += 3;
				if(objectData[i][1].matches("./{1}.")){
					usesMTL = true;
				}
				if(objectData[i][1].matches("./{1}./{1}.")){
					usesNormals = true;
					usesMTL = true;
				}
			}
			if(objectData[i][0].equals("vn")){
				normalCount += 3;
			}
			if(objectData[i][0].equals("vt")){
				uvCount += 2;					
			}
		}
		float[] verticies = new float[vertexCount];
		float[] uvVerticies = new float[uvCount];
		float[] normalVerticies = new float[normalCount];
		int[] vertexIndicies = new int[indexCount];
		int[] uvVertexIndicies = new int[indexCount];
		int[] normalIndicies = new int[indexCount];
		
		int verticiesNext = 0;
		int vertexIndiciesNext = 0;
		int uvVertexIndiciesNext = 0;
		int uvVerticiesNext = 0;
		int normalIndiciesNext = 0;
		int normalNext = 0;
		for(int i = 0; i < objectData.length; i++){
			if(objectData[i][0].equals("v")){
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][1]);
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][2]);
				verticies[verticiesNext++] = Float.parseFloat(objectData[i][3]);				
			}
			
			if(objectData[i][0].equals("f")){
				if(usesNormals){
					String[] combinedIndicies;
					for(int j = 1; j < 4; j++){
						combinedIndicies = objectData[i][j].split("/");
						vertexIndicies[vertexIndiciesNext++] = Integer.parseInt(combinedIndicies[0]);
						uvVertexIndicies[uvVertexIndiciesNext++] = Integer.parseInt(combinedIndicies[1]);
						normalIndicies[normalIndiciesNext++] = Integer.parseInt(combinedIndicies[2]);
					}
				} else if(usesMTL){
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
			
			if(usesNormals && objectData[i][0].equals("vn")){
				normalVerticies[normalNext++] = Float.parseFloat(objectData[i][1]);
				normalVerticies[normalNext++] = Float.parseFloat(objectData[i][2]);
				normalVerticies[normalNext++] = Float.parseFloat(objectData[i][3]);
			}
			
			if(usesMTL && objectData[i][0].equals("vt")){
				uvVerticies[uvVerticiesNext++] = Float.parseFloat(objectData[i][1]);
				uvVerticies[uvVerticiesNext++] = Float.parseFloat(objectData[i][2]);
			}
		}
		
		float[] objectBuffer;
		
		if(usesNormals){
			objectBuffer = new float[indexCount * 8];
		} else if(usesMTL){
			objectBuffer = new float[indexCount * 5];
		} else{
			objectBuffer = new float[indexCount * 3];
		}
		
		for(int i = 0; i < indexCount; i++){
			objectBuffer[(i * 3) + 0] = verticies[(vertexIndicies[i] - 1) * 3 + 0];
			objectBuffer[(i * 3) + 1] = verticies[(vertexIndicies[i] - 1) * 3 + 1];
			objectBuffer[(i * 3) + 2] = verticies[(vertexIndicies[i] - 1) * 3 + 2];
		}
		
		if(usesMTL){
			for(int i = 0; i < indexCount; i++){
				objectBuffer[(i * 2) + 0 + indexCount * 3] = uvVerticies[(uvVertexIndicies[i] - 1) * 2 + 0];
				objectBuffer[(i * 2) + 1 + indexCount * 3] = 1 - uvVerticies[(uvVertexIndicies[i] - 1) * 2 + 1];
				
			}
		}
		
		if(usesNormals){
			for(int i = 0; i < indexCount; i++){
				objectBuffer[(i * 3) + 0 + indexCount * 5] = normalVerticies[(normalIndicies[i] - 1) * 3 + 0];
				objectBuffer[(i * 3) + 1 + indexCount * 5] = normalVerticies[(normalIndicies[i] - 1) * 3 + 1];
				objectBuffer[(i * 3) + 2 + indexCount * 5] = normalVerticies[(normalIndicies[i] - 1) * 3 + 2];
			}
		}
		return new Primitive(objectBuffer, indexCount, GL11.GL_TRIANGLES, ColorMethod.TEXTURE);
	}
}
