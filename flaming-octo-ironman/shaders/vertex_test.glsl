#version 330

layout(location = 0) in vec4 position;	//The position
layout(location = 1) in vec4 color;		//The color variable

out vec4 gl_Position;	//The standard out variable
smooth out vec4 theColor;	//A new output variable

void main()
{
    gl_Position = position;	//Set the output value to the input value
    theColor = color;
}