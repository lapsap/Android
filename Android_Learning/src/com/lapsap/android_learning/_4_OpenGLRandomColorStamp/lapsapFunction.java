package com.lapsap.android_learning._4_OpenGLRandomColorStamp;

import java.util.ArrayList;
import java.util.Random;

public class lapsapFunction {

	static Random random = new Random();
	public static ArrayList<Float> SquarePoints = new ArrayList<Float>();
	public static ArrayList<Float> SquareColors = new ArrayList<Float>();

	public static void touchSquare(float x, float y)
		{
			for(int i=0;i<3;i++) SquareColors.add(random.nextFloat());
			
			int size = 50; //set the size of my Square
			//set x and y to the middle of my touch
			x = x - size/2;
			y = y + size/2;
			//6 points to make 2 triangles, 1 square
			//top left
			SquarePoints.add(x);
			SquarePoints.add(y);
			//bottom left
			SquarePoints.add(x);
			SquarePoints.add(y - size);
			//top right
			SquarePoints.add(x + size);
			SquarePoints.add(y);
			//bottom right
			SquarePoints.add(x + size);
			SquarePoints.add(y - size);
			//bottom left
			SquarePoints.add(x);
			SquarePoints.add(y - size);
			//top right
			SquarePoints.add(x + size);
			SquarePoints.add(y);
		}

	public static float[] getVertices()
		{
			//convert Arraylist touchSquare into float[]
			float[] result = new float[SquarePoints.size()];
			for (int i = 0; i < SquarePoints.size(); i++)
				{
					result[i] = SquarePoints.get(i);
				}

			return result;
		}

}
