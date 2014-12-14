#version 330

const int TEXTURE = 0;
const int COLOR = 1;

in vec2 UV;	//UV coordinates from the vertex shader
in vec3 color;	//Color values from the vertex shader
uniform int colorType = COLOR;	//How to color the fragment

out vec3 outputColor;	//Fragment color output

uniform sampler2D textureSampler;	//Texture to use

void main()
{
	if(colorType == TEXTURE){
		outputColor = texture(textureSampler, UV).rgb;
	} else if(colorType == COLOR){
		outputColor = color;
	} else {
		outputColor = vec3(0.5, 0.5, 0.5);
	}
}