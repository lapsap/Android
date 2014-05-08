package com.lapsap.android_learning._1_dragAndDrop;

import com.lapsap.android_learning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class picture_move_logics extends View {

	int chosenPic = R.drawable.ic_launcher;	//ur pic
	
	Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), chosenPic);	//setting up the bitmap
	float bitmapPositionX, bitmapPositionY, oneFingerX, oneFingerY; // moving
	float old1x, old1y, old0x, old0y, new0x, new0y, new1x, new1y; // zooming
	int zoomingScaleWidth = myBitmap.getWidth(), zoomingScaleHeight = myBitmap.getHeight(), cal; // zooming
	boolean oneFinger = true; //to split 2finger and 1finger

	public picture_move_logics(Context context)
		{
			super(context);

			// TODO Auto-generated constructor stub
		}

	@Override
	protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(myBitmap, bitmapPositionX, bitmapPositionY, null);
		}

	@Override
	public boolean onTouchEvent(MotionEvent event)
		{
			switch (event.getActionMasked())
				{
				case MotionEvent.ACTION_DOWN: //1st pointer click
					if (oneFinger == true)
						{
							// moving logics
							oneFingerX = event.getX(0) - bitmapPositionX; //so that the pic wont jump to my pointer's position
							oneFingerY = event.getY(0) - bitmapPositionY; // invert effects if set to plus
						}

				case MotionEvent.ACTION_POINTER_DOWN: //multi touching starts here
					// zooming logics
					if (event.getPointerCount() == 2)
						{
							oneFinger = false; //disable singer finger moving to prevent bugs
							old0x = event.getX(0); //note down my original pointer position for scaling reference 
							old0y = event.getY(0);
							old1x = event.getX(1);
							old1y = event.getY(1);
						}

					break;
				case MotionEvent.ACTION_MOVE:
					// moving logics
					if (event.getPointerCount() == 1 && oneFinger == true)
						{
							bitmapPositionX = event.getX(0) - oneFingerX;
							bitmapPositionY = event.getY(0) - oneFingerY;
						}

					// Zooming logics
					else if (event.getPointerCount() == 2)
						{
							new0x = event.getX(0);
							new0y = event.getY(0);
							new1x = event.getX(1);
							new1y = event.getY(1);

							//distance between 2 point formula
							float calold = (float) Math.sqrt((old0x - old1x) * (old0x - old1x) + (old0y - old1y) * (old0y - old1y));
							float calnew = (float) Math.sqrt((new0x - new1x) * (new0x - new1x) + (new0y - new1y) * (new0y - new1y));

							//minus the length be4 scaling 
							cal = (int) ((calnew - calold));

							//few rules, so that the pic dont disappear 
							try
								{
									if (zoomingScaleWidth + cal > 200 && zoomingScaleHeight + cal > 200 && zoomingScaleWidth + cal < 2500 && zoomingScaleHeight + cal <2500 )
										{
											myBitmap = BitmapFactory.decodeResource(getResources(), chosenPic);
											myBitmap = Bitmap.createScaledBitmap(myBitmap, zoomingScaleWidth + cal, zoomingScaleHeight + cal, false);
										} else
										cal = 0;
								} catch (Exception e)
								{
									System.out.println("err scalling image. " + e);
								}
						}
					break;

				case MotionEvent.ACTION_POINTER_UP:
					//zooming
					if (event.getPointerCount() == 2)
						{
							zoomingScaleWidth += cal;
							zoomingScaleHeight += cal;
						}
					break;

				case MotionEvent.ACTION_UP: //last finger left
					oneFinger = true;
					break;

				}

			invalidate();
			return true;
		}

}
