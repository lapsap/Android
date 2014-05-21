package com.lapsap.android_learning._4_OpenGLRandomColorStamp;

import android.opengl.GLES20;

public class GLShaders {

	// Program variables
	public static int sp_SolidColor;
	public static float triangleRed = 0f, triangleGreen = .5f, triangleBlue = .5f;	//colors of triangle
	
	public static final String vs_SolidColor =
		"uniform 	mat4 		uMVPMatrix;" +
		"attribute 	vec4 		vPosition;" +
	    "void main() {" +
	    "  gl_Position = uMVPMatrix * vPosition;" +
	    "}";
	
	public static final String fs_SolidColor =
			"uniform vec4 u_Color; " +
		"precision mediump float;" +
	    "void main() {" +
	    "  gl_FragColor = u_Color;" +		// color of triangle
	    "}"; 
	
	
	
	public static int loadShader(int type, String shaderCode){

	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);
	    
	    // return the shader
	    return shader;
	}
}