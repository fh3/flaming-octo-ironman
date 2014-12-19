#version 330

const int TEXTURE = 0;
const int COLOR = 1;

in vec2 UV;	//UV coordinates from the vertex shader
in vec3 color;	//Color values from the vertex shader
uniform int colorType = COLOR;	//How to color the fragment

out vec4 outputColor;	//Fragment color output

uniform sampler2D textureSampler;	//Texture to use

smooth in vec4 vNormal;

struct SimpleDirectionalLight
{
	vec3 vColor;
	vec3 vDirection;
	float fintensity;
};

struct PointLight 
{ 
   vec3 vColor; // Color of that point light
   vec3 vPosition; 
    
   float fAmbient; 

   float fConstantAtt; 
   float fLinearAtt; 
   float fExpAtt; 
}; 


uniform SimpleDirectionalLight directionalLights[] = SimpleDirectionalLight[](SimpleDirectionalLight(vec3(5.0, 1.0, 1.0), vec3(1.0, 1.0, 1.0), 1.0));

void main()
{
	if(colorType == TEXTURE){
		outputColor = texture(textureSampler, UV);
		for(int i = 0; i < directionalLights.length(); i++){
			float diffuseIntensity = clamp(dot(normalize(vNormal), vec4(directionalLights[i].vDirection, 0.0)), 0.0, 1.0);
			outputColor = outputColor * diffuseIntensity * vec4(directionalLights[i].vColor, 0.0) * vec4(directionalLights[i].vColor * (directionalLights[i].fintensity + diffuseIntensity), 0.0);
		}
	} else if(colorType == COLOR){
		outputColor = vec4(color, 0.0);
	} else {
		outputColor = vec4(0.5, 0.5, 0.5, 0.0);
	}
	
}

vec4 getPointLightColor(const PointLight ptLight, vec3 vWorldPos, vec3 vNormal) 
{ 
   vec3 vPosToLight = vWorldPos-ptLight.vPosition; 
   float fDist = length(vPosToLight); 
   vPosToLight = normalize(vPosToLight); 
    
   float fDiffuse = max(0.0, dot(vNormal, -vPosToLight)); 

   float fAttTotal = ptLight.fConstantAtt + ptLight.fLinearAtt*fDist + ptLight.fExpAtt*fDist*fDist; 

   return vec4(ptLight.vColor, 1.0)*(ptLight.fAmbient+fDiffuse)/fAttTotal; 
}