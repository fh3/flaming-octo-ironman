#version 330

out vec4 outputColor;	//Default out variable
void main()
{
	float lerpValue = gl_FragCoord.y / 600.0f; //Divide the y coord by 600, store the result in a new float value
	outputColor = mix(//Mix function performs linear interpolation
						vec4(1.0f, 1.0f, 1.0f, 1.0f),	//Point A
						vec4(0.2f, 0.2f, 0.2f, 1.0f),	//Point B
						lerpValue	//How far between the two points (must be between [0, 1]
						);
}