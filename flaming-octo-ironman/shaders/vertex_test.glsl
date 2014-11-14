#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform vec3 offset;	//How far the camera has moved
uniform mat4 cameraToClipMatrix;	//Used to transform the camera
uniform mat4 modelToCameraMatrix;	//Used to transform the model into worldspace?

void main()
{
	vec4 cameraPos = position + vec4(offset, 0.0);	//Move the camera
	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * offset;
	theColor = color;
}