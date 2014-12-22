#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 vertexUV;
layout(location = 2) in vec3 vertexColor;
layout(location = 3) in vec3 normal;


//--------Vertex position--------
//----Uniform variables----
uniform mat4 pvmMatrix;	//Perspective * view * model matrix (Internet people: MVP doesn't work, it only works this way)
uniform mat4 modelMatrix;	//Model Matrix

//-------------------------------

//--------Fragment shader--------
//----Out variables----
//--Coloring--
out vec2 UV;
out vec3 color;

//--Lighting--
out vec3 vNormal;	//The corrected normal in worldspace
out vec3 surfacePosition;	//The surface's position in worldspace

void main()
{	
	//--------Vertex position--------
	gl_Position = pvmMatrix * vec4(position, 1.0);	//Calculate the vertex's final position in clip space
	//-------------------------------
	
	//--------Fragment shader--------
	//----Coloring----
	UV = vertexUV;
	color = vertexColor;
	
	//----Lighting----
	vNormal = normalize(transpose(inverse(mat3(modelMatrix))) * normal);	//The normal in worldspace, corrected for translating and rotating
	surfacePosition = vec3(modelMatrix * vec4(position, 1.0)).xyz;	//The vertex in worldspace 
	
	//-------------------------------
}