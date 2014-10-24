#version 330

layout(location = 0) in vec4 position;	//The input variable
out vec4 gl_Position;	//The standard out variable
void main()
{
    gl_Position = position;	//Set the output value to the input value
}