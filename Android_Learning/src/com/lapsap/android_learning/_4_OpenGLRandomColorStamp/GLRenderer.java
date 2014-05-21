package com.lapsap.android_learning._4_OpenGLRandomColorStamp;

import static android.opengl.GLES20.GL_TRIANGLES;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class GLRenderer implements Renderer {

	//counting frames per second
	float fps = 0;
	long elapsed = 0;
	// Our matrices
	private final float[] mtrxProjection = new float[16];
	private final float[] mtrxView = new float[16];
	private final float[] mtrxProjectionAndView = new float[16];

	// Geometric variables
	public static float vertices[];
	public FloatBuffer vertexBuffer;

	// Our screenresolution
	float mScreenWidth = 1280;
	float mScreenHeight = 768;

	// Misc
	Context mContext;
	long mLastTime;
	int mProgram;
	Random random = new Random();	//for randoming numbers

	public GLRenderer(Context c)
		{
			mContext = c;
			mLastTime = System.currentTimeMillis() + 100;
		}

	public void onPause()
		{
			/* Do stuff to pause the renderer */
		}

	public void onResume()
		{
			/* Do stuff to resume the renderer */
			mLastTime = System.currentTimeMillis();
		}

	@Override
	public void onDrawFrame(GL10 unused)
		{
			Render(mtrxProjectionAndView); // Render our example

			long now = System.currentTimeMillis(); // Get the current time
			elapsed += now - mLastTime; // Get the amount of time the last frame took.
			mLastTime = now; // Save the current time to see how long it took :).

			if (elapsed < 1000)
				{
					fps++;
				} else
				{
					//System.out.println("fps : "+fps);	//fps for self viewing
					fps = 0; //reset values
					elapsed = 0; //reset values 
				}
		}

	private void Render(float[] m)
		{
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			int mPositionHandle = GLES20.glGetAttribLocation(GLShaders.sp_SolidColor, "vPosition");
			GLES20.glEnableVertexAttribArray(mPositionHandle);
			GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
			int mtrxhandle = GLES20.glGetUniformLocation(GLShaders.sp_SolidColor, "uMVPMatrix");
			GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);
			int uColorLocation = GLES20.glGetUniformLocation(GLShaders.sp_SolidColor, "u_Color");	//;inking the color
			
			for(int i=0;i<lapsapFunction.SquareColors.size()/3;i++){
			GLES20.glUniform4f(uColorLocation, lapsapFunction.SquareColors.get(0+(i*3)), lapsapFunction.SquareColors.get(1+(i*3)), lapsapFunction.SquareColors.get(2+(i*3)), 1.0f);	//chose the color	
			GLES20.glDrawArrays(GL_TRIANGLES, 0+(i*6), 6);	// Draw the Square
			}
			// Disable vertex array
			GLES20.glDisableVertexAttribArray(mPositionHandle);

		}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
		{

			// We need to know the current width and height.
			mScreenWidth = width;
			mScreenHeight = height;

			// Redo the Viewport, making it fullscreen.
			GLES20.glViewport(0, 0, (int) mScreenWidth, (int) mScreenHeight);

			// Clear our matrices
			for (int i = 0; i < 16; i++)
				{
					mtrxProjection[i] = 0.0f;
					mtrxView[i] = 0.0f;
					mtrxProjectionAndView[i] = 0.0f;
				}
			
			// Setup our screen width and height for normal sprite translation.
			Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);

			// Set the camera position (View matrix)
			Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

			// Calculate the projection and view transformation
			Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

		}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{

			// Create the Square
			SetupSquare();

			GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1); //background color
			
			
			// Create the shaders
			int vertexShader = GLShaders.loadShader(GLES20.GL_VERTEX_SHADER, GLShaders.vs_SolidColor);
			int fragmentShader = GLShaders.loadShader(GLES20.GL_FRAGMENT_SHADER, GLShaders.fs_SolidColor);

			GLShaders.sp_SolidColor = GLES20.glCreateProgram(); // create empty OpenGL ES Program
			GLES20.glAttachShader(GLShaders.sp_SolidColor, vertexShader); // add the vertex shader to program
			GLES20.glAttachShader(GLShaders.sp_SolidColor, fragmentShader); // add the fragment shader to program
			GLES20.glLinkProgram(GLShaders.sp_SolidColor); // creates OpenGL ES program executables
			
			// Set our shader programm
			GLES20.glUseProgram(GLShaders.sp_SolidColor);
		}

	public void SetupSquare()
		{
			vertices = lapsapFunction.getVertices();	

			// The vertex buffer.
			ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
			bb.order(ByteOrder.nativeOrder());
			vertexBuffer = bb.asFloatBuffer();
			vertexBuffer.put(vertices);
			vertexBuffer.position(0);


		}

	public void processTouchEvent(MotionEvent event)
		{

			switch (event.getActionMasked())
				{

				case MotionEvent.ACTION_DOWN:
					lapsapFunction.touchSquare(event.getX(), MainActivity.height - event.getY());//need to minus cause, android Y-axis is inverted
					SetupSquare(); //call this method to update the ByteBuffers.
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if(event.getPointerCount()==2){ //if lay down 2 fingers
						
						//sad not working
						GLShaders.triangleRed = random.nextFloat() ;
						GLShaders.triangleGreen = random.nextFloat() ;
						GLShaders.triangleBlue = random.nextFloat() ;
						
					
					}else if (event.getPointerCount() == 3)	//if lay down 3 fingers
						{
							lapsapFunction.SquareColors.clear(); //clear all previous colors
							lapsapFunction.SquarePoints.clear();	//clear all the previous clicked points
							SetupSquare();	//update the values
						}
					break;
				}
		}

}