#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform mat4 cameraToClipMatrix;	//Used to transform the camera
uniform mat4 modelToCameraMatrix;	//Used to transform the model into worldspace?
uniform vec3 cameraPosition;	//The camera's current position
uniform vec3 point;	//The point to look at
uniform vec3 upVector;	//The up vector

void main()
{	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix * position;
	theColor = color;
}