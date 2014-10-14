package com.flamingOctoIronman.subsystem.tools;

import java.io.File;
import java.io.IOException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaCompiler {
	private static Globals globals;
	private static Process compiler;
	private static File dir = new File(new File(System.getProperty("user.dir")).getParentFile().getPath() + "\\flaming-octo-ironman");
	private static File libs = new File(dir.getAbsolutePath() + "/libraries/luaj-jse-3.0.jar");
	private static File src = new File(dir.getAbsolutePath() + "/scripts/source/");
	private static File dst = new File(dir.getAbsolutePath() + "/scripts/compiled/");
	private static String[] arguments = {"java", " -cp " + libs.getAbsolutePath() + " luac -o " + dst.getAbsolutePath() + src.getAbsolutePath()};
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]){
		globals = JsePlatform.standardGlobals();
		System.out.println("Attempting to compile scripts...");
		System.out.println(" -cp " + libs.getAbsolutePath() + " luac -s " + src.getAbsolutePath() + " -d " + dst.getAbsolutePath());
		try {
			compiler = Runtime.getRuntime().exec(arguments);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Compilation failed!");
			Thread.currentThread().stop();
		}
		System.out.println("Done compiling scripts.");
	}
}
