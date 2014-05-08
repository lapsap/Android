package com.lapsap.android_learning._2_OpenGLSquareFollowTouch;


public class lapsapFunction {

	public static float[] placeSquare(float x, float y)
		{
			int size = 300;	//size of the square
			x-=size/2; y+=size/2; 	//to get the middle square
			
			float[] result = { x, y, x, y - size, x + size, y - size, x + size, y, };
			return result;
		}

	

}
