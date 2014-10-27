#version 330

smooth in vec4 theColor;	//New input variable, mast have same name

out vec4 outputColor;	//Default out variable

void main()
{
	outputColor = theColor;
}