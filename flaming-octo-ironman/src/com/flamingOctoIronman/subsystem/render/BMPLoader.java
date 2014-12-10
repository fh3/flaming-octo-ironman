package com.flamingOctoIronman.subsystem.render;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.flamingOctoIronman.FlamingOctoIronman;
import com.flamingOctoIronman.subsystem.debugging.DebuggingManager;
import com.flamingOctoIronman.subsystem.debugging.StreamManager;
import com.flamingOctoIronman.subsystem.render.primitives.Texture;
import com.flamingOctoIronman.subsystem.resource.ResourceManager;

public class BMPLoader {
	private static StreamManager out = null;
	public static Texture loadBMP(File f){
		Texture t = null;
		if(out == null){
			out = ((DebuggingManager) FlamingOctoIronman.getCoreManager(DebuggingManager.class.getSimpleName())).getStreamManager();
		}
		byte[] header = new byte[54];
		
		try(FileInputStream input = ResourceManager.getFileInputStream(f)){
			for(int i = 0; i < header.length; i++){
				header[i] = (byte) input.read();
			}
			if(header[0] != 'B' && header[1] != 'M'){
				out.println("Not a bitmap file!");
				throw new IOException();
			}
			int dataPosistion = header[0x0A], imageSize = header[0x22], width = header[0x12], height = header[0x16];
			if(imageSize == 0){
				imageSize = width * height;
			}
			if(dataPosistion == 0){
				dataPosistion = 54;
			}
			byte[] data = new byte[49152];
			input.read(data);
			t = new Texture(data, imageSize, width, height);
		} catch (IOException e) {
			out.println("Cound not load file: " + f.getAbsolutePath());
			out.printError(e.getMessage());
		}
		return t;
	}
}
