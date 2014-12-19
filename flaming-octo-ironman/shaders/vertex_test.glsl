#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 vertexUV;
layout(location = 2) in vec3 vertexColor;
layout(location = 3) in vec3 normal;

out vec2 UV;
out vec3 color;

uniform mat4 perspectiveMatrix;	//Perspective Matrix
uniform mat4 modelMatrix;	//Model Matrix
uniform mat4 viewMatrix;	//View Matrix

smooth out vec4 vNormal;

void main()
{	
	gl_Position = perspectiveMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
	
	vNormal = modelMatrix * viewMatrix * perspectiveMatrix * vec4(normal, 0.0);
	
	UV = vertexUV;
	color = vertexColor;
}