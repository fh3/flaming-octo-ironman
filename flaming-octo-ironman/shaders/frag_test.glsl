#version 330

const int TEXTURE = 0;
const int COLOR = 1;

in vec2 UV;	//UV coordinates from the vertex shader
in vec3 color;	//Color values from the vertex shader
uniform int colorType = COLOR;	//How to color the fragment

out vec4 outputColor;	//Fragment color output

uniform sampler2D textureSampler;	//Texture to use

uniform vec3 cameraPosition;

smooth in vec3 vNormal;

smooth in vec3 position_worldspace;

struct DirectionalLight
{
	vec3 vColor;	//The light's color
	vec3 vDirection;	//The light's direction
	float fintensity;	//The light's strength
};

struct PointLight 
{ 
   vec3 position; //The light's position
   float intensity; //The light's strength
   float ambient;	//The light's ambient coefficient
}; 

struct Material
{
	float shine;
	vec3 specularColor;
};

uniform DirectionalLight directionalLights[] = DirectionalLight[](DirectionalLight(vec3(1.0, 1.0, 1.0), vec3(1.0, 1.0, 1.0), 1.0));

uniform PointLight pointLight = PointLight(vec3(0.0, 100.0, 0.0), 2.0, 0.5);

uniform Material material = Material(1.0, vec3(1.0, 1.0, 1.0));

void main()
{
	vec4 surfaceColor;

	//Calculate the color based off of texture/passed color/none
	if(colorType == TEXTURE){
		surfaceColor = texture(textureSampler, UV);
	} else if(colorType == COLOR){
		surfaceColor = vec4(color, 0.0);
	} else {
		surfaceColor = vec4(0.5, 0.5, 0.5, 0.0);
	}
	/*
	//Apply directional lighting
	if(colorType == TEXTURE){
		for(int i = 0; i < directionalLights.length(); i++){
			float diffuseIntensity = max(0.0, dot(vNormal, directionalLights[i].vDirection));
			outputColor = vec4(outputColor.rgb * diffuseIntensity * directionalLights[i].vColor * (directionalLights[i].vColor * (directionalLights[i].fintensity + diffuseIntensity)), outputColor.a);
		}
	}*/
	//Apply point lights
	vec3 diffuse;
	vec3 ambient;
	if(colorType == TEXTURE){	 
		//----Diffuse----
	    //Calculate the vector from this pixels surface to the light source
	    vec3 surfaceToLight = normalize(pointLight.position - position_worldspace);
	
	    //Calculate the cosine of the angle of incidence
	    float brightness = max(0.0, dot(vNormal, surfaceToLight));
	
	    //Calculate final color of the pixel
	    diffuse = brightness * pointLight.intensity * surfaceColor.rgb;
	    
	    //----Ambient----
	    //Calculate the ambient component
	    ambient = pointLight.ambient * surfaceColor.xyz * pointLight.intensity;
	    
	    //----Specular----
		float specularCoefficient = 0.0;
		if(brightness > 0.0){
		    specularCoefficient = pow(max(0.0, dot(normalize(, reflect(-surfaceToLight, normal))), material.shine);
		}
		vec3 surfaceToCamera = normalize(cameraPosition - surfacePosition);
		vec3 specular = material.specularColor * specularCoefficient * pointLight.intensities;
	}
	
	if(colorType == TEXTURE){
		outputColor = vec4(diffuse, surfaceColor.a);
	} else if(colorType == COLOR){
		outputColor = surfaceColor;
	} else {
		outputColor = surfaceColor;
	}
}