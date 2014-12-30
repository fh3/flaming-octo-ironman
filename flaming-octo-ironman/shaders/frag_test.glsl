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
//----Definitions----
#define MAX_LIGHTS 1
//----In variables----
in vec3 vNormal;	//The vertex normal in worldspace
in vec3 surfacePosition;	//The surface's position in worldspace

//----Structures----
struct Light {
   vec4 position;
   vec3 intensities; //a.k.a the color of the light
   float attenuation;
   float ambientCoefficient;
   float coneAngle;
   vec3 coneDirection;
};

struct Material	//Structure for material
{
	float shine;	//The material's shine
	vec3 specularColor;	//The material's specular color
};

//----Uniforms----
uniform vec3 cameraPos;	//The camera's current position in worldspace
uniform Light allLights[MAX_LIGHTS] = {Light(vec4(0.0, 1.0, 0.0, 1.0), vec3(1.0, 1.0, 1.0), 1.0, 0.5, 45, vec3(0.0, -1.0, 0.0))};
uniform int numLights = 1;	//Total number of lights

uniform Material material = Material(50, vec3(1.0, 1.0, 1.0));	//The material (or default)
//------------------------

//--------Final color--------
out vec4 outputColor;	//Fragment color output
//---------------------------

vec3 ApplyLight(Light light, vec3 surfaceColor, vec3 normal, vec3 surfacePos, vec3 surfaceToCamera) {
    vec3 surfaceToLight;
    float attenuation = 1.0;
    if(light.position.w == 0.0) {
        //directional light
        surfaceToLight = normalize(light.position.xyz);
        attenuation = 1.0; //no attenuation for directional lights
    } else {
        //point light
        surfaceToLight = normalize(light.position.xyz - surfacePos);
        float distanceToLight = length(light.position.xyz - surfacePos);
        attenuation = 1.0 / (1.0 + light.attenuation * pow(distanceToLight, 2));

        //cone restrictions (affects attenuation)
        float lightToSurfaceAngle = degrees(acos(dot(-surfaceToLight, normalize(light.coneDirection))));
        if(lightToSurfaceAngle > light.coneAngle){
            attenuation = 0.0;
        }
    }

    //ambient
    vec3 ambient = light.ambientCoefficient * surfaceColor.rgb * light.intensities;

    //diffuse
    float diffuseCoefficient = max(0.0, dot(normal, surfaceToLight));
    vec3 diffuse = diffuseCoefficient * surfaceColor.rgb * light.intensities;
    
    //specular
    float specularCoefficient = 0.0;
    if(diffuseCoefficient > 0.0)
        specularCoefficient = pow(max(0.0, dot(surfaceToCamera, reflect(-surfaceToLight, normal))), material.shine);
    vec3 specular = specularCoefficient * material.specularColor * light.intensities;

    //linear color (color before gamma correction)
    return ambient + attenuation*(diffuse + specular);
}

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
	vec3 surfaceToCamera = normalize(cameraPos - surfacePosition); //Vector from camera to surface
	surfaceToCamera = vec3(-surfaceToCamera.x, -surfaceToCamera.y, surfaceToCamera.z);
	
	vec3 pointFinalColor = vec3(0);;	//The final color based on lighting
	if(colorType == TEXTURE){	 
		for(int i = 0; i < numLights; ++i){
		    pointFinalColor += ApplyLight(allLights[i], surfaceColor.rgb, vNormal, surfacePosition, surfaceToCamera);
		}
	}
	
	//------------------------
	//--------Sets the final fragment color--------
	if(colorType == TEXTURE){
		outputColor = vec4(pointFinalColor, surfaceColor.a);
	} else if(colorType == COLOR){
		outputColor = surfaceColor;
	} else {
		outputColor = surfaceColor;
	}
	//---------------------------------------------
	
}