#version 330

in vec2 UV;	//Texture location input variable

out vec3 outputColor;	//Fragment color output

uniform sampler2D myTextureSampler;	//Texture to use

void main()
{
	outputColor = texture(myTextureSampler, UV).rgb;
}