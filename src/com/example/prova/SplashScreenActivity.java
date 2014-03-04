package com.example.prova;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity {

	private long splashTemps = 100; //3 segons
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setBackgroundDrawableResource(R.drawable.ic_action_refresh);
		setContentView(R.layout.activity_splash_screen);
		
		//que es mostri full screen
		
		
		TimerTask tasca = new TimerTask() {
			
			@Override
			public void run() {
				Intent intent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
		        startActivity(intent);
		        finish();
				
			}
		};
		
		Timer timer = new Timer();
	    timer.schedule(tasca, splashTemps);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
