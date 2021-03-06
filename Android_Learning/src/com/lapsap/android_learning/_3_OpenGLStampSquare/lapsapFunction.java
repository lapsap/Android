package com.lapsap.android_learning._3_OpenGLStampSquare;

import java.util.ArrayList;

public class lapsapFunction {

	public static ArrayList<Float> SquarePoints = new ArrayList<Float>();

	public static void touchSquare(float x, float y)
		{
			int size = 50; //set the size of my Square

			//creating 4 points around my touch (setting my touch to the middle)
			SquarePoints.add(x);
			SquarePoints.add(y);
			SquarePoints.add(x);
			SquarePoints.add(y - size);
			SquarePoints.add(x + size);
			SquarePoints.add(y - size);
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

	public static short[] getIndices()
		{
			//indices will be quite complex,because openGL is drawing 2 Square instead or a square
			//so the way we link it together need to be diff, 6 links instead of 4
			
			//Divide by 8 because a square have 4 points and each point have X and Y values
			//Multiply by 6 because we need 6 link to draw 2 Squares
			short[] result = new short[(SquarePoints.size() / 8) * 6];	 
	
			int tempCount =0;
			for (int i = 0; i < result.length; i += 6)
				{
					result[0 + i] = (short) (tempCount + 0 );
					result[1 + i] = (short) (tempCount + 1);
					result[2 + i] = (short) (tempCount + 2);
					result[3 + i] = (short) (tempCount + 0);
					result[4 + i] = (short) (tempCount + 2);
					result[5 + i] = (short) (tempCount + 3);
					tempCount +=4;
				}

			
			return result;
		}

}
