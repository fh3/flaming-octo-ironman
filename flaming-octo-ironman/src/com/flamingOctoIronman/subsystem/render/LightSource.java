package com.flamingOctoIronman.subsystem.render;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LightSource {
	private static ShaderProgram program;
	
	public LightSource(
						Vector4f ambient,
						Vector4f diffuse,
						Vector4f position,
						Vector4f halfVector,
						Vector3f spotDirection,
						float spotExponent,
						float spotCutoff,
						float spotCosCutoff,
						float constantAttenuation,
						float linearAttenuation,
						float quadraticAttenuation
						){
		
	}
	
	public static void setShaderProgram(ShaderProgram program){
		LightSource.program = program;
	}
}
