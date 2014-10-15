package com.flamingOctoIronman.subsystem.debugging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class PrintStreamIntercepter extends PrintStream{
	static FileOutputStream nul = null;
	public PrintStreamIntercepter(){
		super(new OutputStream(){
			@Override
			public void write(int b) throws IOException {
				
			}
		});
	}
}
