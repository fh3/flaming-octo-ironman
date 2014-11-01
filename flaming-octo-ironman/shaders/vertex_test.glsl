#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform vec2 offset;	//How far the camera has moved
uniform float zNear;	//Closest didstance while still rendered
uniform float zFar;		//Max distance while still rendered
uniform float frustumScale;	//Adjust the scale of the scene

void main()
{
	vec4 cameraPos = position + vec4(offset, 0.0, 0.0);	//Move the camera
	vec4 clipPos;	//Create a new vector for the clip position
	
	clipPos.xy = cameraPos.xy * frustumScale;
	clipPos.z = cameraPos.z * (zNear + zFar) / (zNear - zFar) + (2 * zNear * zFar) / (zNear - zFar);
	clipPos.w = -cameraPos.z;
	
	gl_Position = clipPos;
	theColor = color;
}