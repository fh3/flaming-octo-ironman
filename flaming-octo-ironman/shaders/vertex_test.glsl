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
	mat4 cameraMatrix;
	cameraMatrix[0][3] = 0.0;
	cameraMatrix[1][3] = 0.0;
	cameraMatrix[2][3] = 0.0;
	cameraMatrix[3][0] = 0.0;
	cameraMatrix[3][1] = 0.0;
	cameraMatrix[3][2] = 0.0;
	cameraMatrix[3][3] = 1.0;
	vec3 forward = normalize(point - cameraPosition);
	vec3 side = normalize(cross(forward, upVector));
	vec3 up = normalize(cross(side, forward));
	cameraMatrix[0][0] = side.x;
	cameraMatrix[0][1] = side.y;
	cameraMatrix[0][2] = side.z;
	cameraMatrix[1][0] = up.x;
	cameraMatrix[1][1] = up.y;
	cameraMatrix[1][2] = up.z;
	cameraMatrix[2][0] = -forward.x;
	cameraMatrix[2][1] = -forward.y;
	cameraMatrix[2][2] = -forward.z;
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix * position;
	theColor = color;
}