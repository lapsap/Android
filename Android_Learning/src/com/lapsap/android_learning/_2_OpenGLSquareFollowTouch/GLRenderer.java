package com.lapsap.android_learning._2_OpenGLSquareFollowTouch;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class GLRenderer implements Renderer {

	//counting frames per second
	int fps =0;
	long elapsed =0;
	// Our matrices
	private final float[] mtrxProjection = new float[16];
	private final float[] mtrxView = new float[16];
	private final float[] mtrxProjectionAndView = new float[16];

	// Geometric variables
	public static float vertices[];
	public static short indices[];
	public FloatBuffer vertexBuffer;
	public ShortBuffer drawListBuffer;

	// Our screenresolution
	float mScreenWidth = 1280;
	float mScreenHeight = 768;

	// Misc
	Context mContext;
	long mLastTime;
	int mProgram;

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
			Render(mtrxProjectionAndView);	// Render our example
						
			
			long now = System.currentTimeMillis();	// Get the current time
			elapsed += now - mLastTime;	// Get the amount of time the last frame took.
			mLastTime = now;	// Save the current time to see how long it took :).
		  
			if(elapsed<1000){
				fps++;
			}else{			
				//System.out.println("fps : "+fps);	//fps for self viewing
				fps=0;			//reset values
				elapsed=0;		//reset values 
			}
		}

	private void Render(float[] m)
		{
			
			// clear Screen and Depth Buffer, we have set the clear color as black.
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

			// get handle to vertex shader's vPosition member
			int mPositionHandle = GLES20.glGetAttribLocation(GLShaders.sp_SolidColor, "vPosition");

			// Enable generic vertex attribute array
			GLES20.glEnableVertexAttribArray(mPositionHandle);

			// Prepare the triangle coordinate data
			GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
			
			
			// Get handle to shape's transformation matrix
			int mtrxhandle = GLES20.glGetUniformLocation(GLShaders.sp_SolidColor, "uMVPMatrix");

			// Apply the projection and view transformation
			GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

			// Draw the triangle
			GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

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

			// Create the triangle
			SetupSquare(500,500);

			// Set the clear color to black
			GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);

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

	public void SetupSquare(float x,float y)
		{
			// We have to create the vertices of our triangle.
			vertices = lapsapFunction.placeSquare(x, y);

			indices = new short[] { 0, 1, 2, 0, 2, 3 }; // The order of vertexrendering.

			// The vertex buffer.
			ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
			bb.order(ByteOrder.nativeOrder());
			vertexBuffer = bb.asFloatBuffer();
			vertexBuffer.put(vertices);
			vertexBuffer.position(0);

			// initialize byte buffer for the draw list
			ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
			dlb.order(ByteOrder.nativeOrder());
			drawListBuffer = dlb.asShortBuffer();
			drawListBuffer.put(indices);
			drawListBuffer.position(0);

		}

	public void processTouchEvent(MotionEvent event)
		{
			//need to minus cause, android Y-axis is inverted
			SetupSquare(event.getX(),MainActivity.height-event.getY());
		}

	
}