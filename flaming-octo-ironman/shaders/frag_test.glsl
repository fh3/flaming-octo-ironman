#version 330

const int TEXTURE = 0;
const int COLOR = 1;

in vec2 UV;	//UV coordinates from the vertex shader
in vec3 color;	//Color values from the vertex shader
uniform int colorType = COLOR;	//How to color the fragment

out vec3 outputColor;	//Fragment color output

uniform sampler2D textureSampler;	//Texture to use

uniform int lightColor = 1;	//Light color
uniform int lightPower = 1;	//Light power

uniform vec3 lightSource;

in vec3 Normal_cameraspace;
in vec3 LightDirection_cameraspace;
in vec4 Position_worldspace;

void main()
{
	if(colorType == TEXTURE){
		outputColor = texture(textureSampler, UV).rgb;
	} else if(colorType == COLOR){
		outputColor = color;
	} else {
		outputColor = vec3(0.5, 0.5, 0.5);
	}
	
	float fragDistance = distance(Position_worldspace.xyz, lightSource);
	
	outputColor = outputColor * lightColor * lightPower * clamp( dot( normalize(Normal_cameraspace), normalize(LightDirection_cameraspace) ), 0,1 ) / (fragDistance * fragDistance);
}