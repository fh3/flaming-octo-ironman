#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform mat4 cameraToClipMatrix;	//Used to transform the camera
uniform mat4 modelToCameraMatrix;	//Used to transform the model into worldspace?
uniform mat4 cameraMatrix;	//The cameras atomic transformation matrix

void main()
{	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix* position;
	theColor = color;
}