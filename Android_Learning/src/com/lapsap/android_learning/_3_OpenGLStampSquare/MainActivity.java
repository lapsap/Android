package com.lapsap.android_learning._3_OpenGLStampSquare;
//http://androidblog.reindustries.com/a-real-open-gl-es-2-0-2d-tutorial-part-1/

import com.lapsap.android_learning.R;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Our OpenGL Surfaceview
	private GLSurfaceView glSurfaceView;
	static int width,height;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//hints
		Toast.makeText(this, "Lay down 3 fingers to clear screen.", Toast.LENGTH_LONG).show();
		//get heigh and width
		DisplayMetrics dimension = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dimension);
		width = dimension.widthPixels;
		height = dimension.heightPixels;
		
		// Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Super
		super.onCreate(savedInstanceState);
		
		// Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // We create our Surfaceview for our OpenGL here.
        glSurfaceView = new GLSurf(this);
        
        //used the same xml as the previous activity
		setContentView(R.layout.lapsap_2);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Lapsap_2_gamelayout);
        
        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(glSurfaceView, glParams);
	}

	@Override
	protected void onPause() {
		super.onPause();		
		glSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

}