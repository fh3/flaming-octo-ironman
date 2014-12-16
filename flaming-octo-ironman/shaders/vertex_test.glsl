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

void main()
{	
	gl_Position = cameraToClipMatrix * modelToCameraMatrix * cameraMatrix * vec4(position, 1.0);
	
	//The normal in eye space
	vec3 n = (cameraToClipMatrix * modelToCameraMatrix * vec4(normal, 0.0)).xyz;
	//The light's direction vector normalized
	vec3 lightDirection = normalize(gl_LightSource[0].position).xyz;
	//The angle between n and lightDirection
	float nLightDirectionAngle = max(dot(n, lightDirection), 0.0);
	
	//Compute the diffuse color
	vec4 diffuse = gl_FrontMaterial.diffuse * gl_LightSource[0].diffuse;
	
	gl_FrontColor = nLightDirectionAngle * diffuse;
	
	UV = vertexUV;
	color = vertexColor;
}