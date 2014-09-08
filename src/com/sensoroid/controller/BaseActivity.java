package com.sensoroid.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sensoroid.helper.OnSwipeTouchListener;

public class BaseActivity extends Activity
{
	private static final String TAG = "BaseActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/****************************For Handling Swipe Events****************************/
	void swipeHandler(Intent intent, View v, Context c)
	{
		try 
		{
			if(intent.getStringExtra("parent").equalsIgnoreCase("home"))
			{
				final Context finalContext = c;
				final Intent iLeft = new Intent(c, ProximityActivity.class);
				final Intent iRight = new Intent(c, AmbientLightActivity.class);
				final Intent iUp = new Intent(c, AccelerometerActivity.class);
				final Intent iDown = new Intent(c, MagnetometerActivity.class);

		    	final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
				v.setOnTouchListener(new OnSwipeTouchListener(c) {
				    @Override
				    public void onSwipeLeft() {
				    	Sensor proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
				    	if (proxSensor != null)
				    	{	
				    		startActivity(iLeft);
				    		finish();
				    	}
				    	else
							Toast.makeText(finalContext, "Your device does not have a proximity sensor!", Toast.LENGTH_SHORT).show();
				    }
				    @Override
				    public void onSwipeRight() {
				    	Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
				    	if (lightSensor != null)
				    	{	
				    		startActivity(iRight);
				    		finish();
				    	}	
				    	else
							Toast.makeText(finalContext, "Your device does not have a light sensor!", Toast.LENGTH_SHORT).show();
				    }
				    @Override
				    public void onSwipeUp() {
				    	Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				    	if (accelerometerSensor != null)
							startActivity(iUp);
				    	else
							Toast.makeText(finalContext, "Your device does not have an accelerometer!", Toast.LENGTH_SHORT).show();
				    	
				    }
				    @Override
				    public void onSwipeDown() {
				    	Sensor magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
				    	if (magnetSensor != null)
				    	{	
				    		startActivity(iDown);
				    		finish();
				    	}
				    	else
							Toast.makeText(finalContext, "Your device does not have a magnetometer!", Toast.LENGTH_SHORT).show();
				    }
				});
			}
			if(intent.getStringExtra("parent").equalsIgnoreCase("proximity"))
			{
				final Intent iRight = new Intent(c, HomeActivity.class);
				v.setOnTouchListener(new OnSwipeTouchListener(c) {
				    @Override
				    public void onSwipeRight() {
						startActivity(iRight);
						finish();
				    }
				});
			}
			if(intent.getStringExtra("parent").equalsIgnoreCase("ambient"))
			{
				final Intent ileft = new Intent(c, HomeActivity.class);
				v.setOnTouchListener(new OnSwipeTouchListener(c) {
				    @Override
				    public void onSwipeLeft() {
						startActivity(ileft);
						finish();
				    }
				});
			}
			if(intent.getStringExtra("parent").equalsIgnoreCase("accelero"))
			{
				final Intent iDown = new Intent(c, HomeActivity.class);
				v.setOnTouchListener(new OnSwipeTouchListener(c) {
				    @Override
				    public void onSwipeDown() {
						startActivity(iDown);
						finish();
				    }
				});
			}
			if(intent.getStringExtra("parent").equalsIgnoreCase("magneto"))
			{
				final Intent iUp = new Intent(c, HomeActivity.class);
				v.setOnTouchListener(new OnSwipeTouchListener(c) {
				    @Override
				    public void onSwipeUp() {
						startActivity(iUp);
						finish();
				    }
				});
			}
		} 
		catch (Exception e)
		{
			Log.i(TAG, "Error in swipeHandler : "+e.toString());
			Toast.makeText(this, "Exception in BaseActivity:swipeHandler : " + e.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
