#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 vertexUV;
layout(location = 2) in vec3 vertexColor;
layout(location = 3) in vec3 normal;

out vec2 UV;
out vec3 color;

uniform mat4 cameraToClipMatrix;	//View Matrix
uniform mat4 modelToCameraMatrix;	//Model Matrix
uniform mat4 cameraMatrix;	//Perspective Matrix

uniform vec3 lightSource;

out vec3 Normal_cameraspace;
out vec3 LightDirection_cameraspace;
out vec4 Position_worldspace;

void main()
{	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix * vec4(position, 1.0);
	
	//Vector's worldspace position
	Position_worldspace = modelToCameraMatrix * vec4(position, 1.0);
	
	//Vector from vertex to camera
	vec3 EyeDirection_cameraspace = vec3(0,0,0) - ( cameraToClipMatrix * modelToCameraMatrix * vec4(position, 1.0)).xyz;
	
	//Vector from vertex to light
	LightDirection_cameraspace = ( cameraToClipMatrix * vec4(lightSource,1)).xyz + EyeDirection_cameraspace;
	
	//Normal of vertex in camera space
	Normal_cameraspace = ( cameraToClipMatrix * modelToCameraMatrix * vec4(normal,0)).xyz;
	
	UV = vertexUV;
	color = vertexColor;
}