#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform vec2 offset;	//How far the camera has moved
uniform mat4 perspectiveMatrix;	//Used to transform the camera

void main()
{
	vec4 cameraPos = position + vec4(offset, 0.0, 0.0);	//Move the camera
	
	gl_Position = perspectiveMatrix * cameraPos;
	theColor = color;
}