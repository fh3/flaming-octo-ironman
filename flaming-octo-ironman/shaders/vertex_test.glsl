#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 vertexUV;
layout(location = 2) in vec3 vertexColor;

out vec2 UV;
out vec3 color;

uniform mat4 cameraToClipMatrix;	//Used to transform the camera
uniform mat4 modelToCameraMatrix;	//Used to transform the model into worldspace?
uniform mat4 cameraMatrix;	//The cameras atomic transformation matrix

void main()
{	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix * vec4(position, 1.0);
	UV = vertexUV;
	color = vertexColor;
}