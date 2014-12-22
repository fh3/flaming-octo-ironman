#version 330

//--------Color from texture--------
//----Constants----
const int TEXTURE = 0;
const int COLOR = 1;

//----In variables----
in vec2 UV;	//UV coordinates from the vertex shader
in vec3 color;	//Color values from the vertex shader

//----Uniform variables----
uniform int colorType;	//How to color the fragment
uniform sampler2D textureSampler;	//Texture to use
//---------------------------------

//--------Lighting--------
//----In variables----
in vec3 vNormal;	//The vertex normal in worldspace
in vec3 surfacePosition;	//The surface's position in worldspace

//----Structures----
struct PointLight	//Structure for point light
{ 
   vec3 position; //The light's position
   float intensity; //The light's strength
   float ambientCoefficient;	//The light's ambient coefficient
   float attenuation;	//The light's intensity over distance
}; 

struct Material	//Structure for material
{
	float shine;	//The material's shine
	vec3 specularColor;	//The material's specular color
};

//----Uniforms----
uniform PointLight pointLight = PointLight(vec3(0.0, 0.0, 1.0), 1.0, 0.01, 0);	//The point light
uniform vec3 cameraPos;	//The camera's current position in worldspace

uniform Material material = Material(1.5, vec3(1.0, 1.0, 1.0));	//The material (or default)
//------------------------

//--------Final color--------
out vec4 outputColor;	//Fragment color output
//---------------------------

void main()
{
	vec4 debugColor;
	//----------Color from texture--------
	//Sets surfaceColor the the appropriate color from either the passed color or texture, defaults to grey
	vec4 surfaceColor;
	
	if(colorType == TEXTURE){
		surfaceColor = texture(textureSampler, UV);
	} else if(colorType == COLOR){
		surfaceColor = vec4(color, 0.0);
	} else {
		surfaceColor = vec4(0.5, 0.5, 0.5, 0.0);
	}
	//------------------------------------
	
	//--------Lighting--------
	//Lighting is only applied if a texture is being used (not color or default)
	
	//----Point lights----
	//Sets pointFinalColor to the surface color as it's effected by the point light
	vec4 pointFinalColor;
	if(colorType == TEXTURE){	 
		vec3 surfaceToLight = normalize(pointLight.position - surfacePosition);	//Vector from surface to light
    	vec3 surfaceToCamera = normalize(cameraPos - surfacePosition);	//Vector from camera to surface
    	
    	//--Ambient--
    	vec3 ambient = pointLight.ambientCoefficient * surfaceColor.rgb * pointLight.intensity;
    	
    	//--Diffuse--
    	float diffuseCoefficient = max(0.0, dot(vNormal, surfaceToLight));
    	vec3 diffuse = diffuseCoefficient * surfaceColor.rgb * pointLight.intensity;
    	
    	//--Specular--
    	float specularCoefficient = 0.0;
    	vec3 surfaceToCamera_modified = vec3(-surfaceToCamera.x, -surfaceToCamera.y, surfaceToCamera.z);
    	if(diffuseCoefficient > 0.0){	//No point in computing specular if diffuse is less than 0.0
    		specularCoefficient = pow(max(0.0, dot(surfaceToCamera_modified, reflect(-surfaceToLight, vNormal))), material.shine);
    	}
    	vec3 specular = specularCoefficient * material.specularColor * pointLight.intensity;
    	
    	//--Attenuation--
    	float distanceToLight = length(surfaceToLight);	//Distance from surface to light
   		float attenuation = 1.0 / (1.0 + pointLight.attenuation * pow(distanceToLight, 2));	//Formula: 1.0/(1.0 + kd^2)
   		
   		//--Gamma--
   		vec3 gamma = vec3(1.0/2.2);
   		
   		//--Output--
   		vec3 linearColor = ambient + attenuation * (diffuse + specular);	//Color before gamma correction
   		pointFinalColor = vec4(pow(linearColor, gamma), surfaceColor.a);
	}
	//------------------
	
	//------------------------
	
	//--------Sets the final fragment color--------
	if(colorType == TEXTURE){
		outputColor = pointFinalColor;
	} else if(colorType == COLOR){
		outputColor = surfaceColor;
	} else {
		outputColor = surfaceColor;
	}
	//---------------------------------------------
}