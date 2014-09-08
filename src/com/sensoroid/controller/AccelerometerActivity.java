package com.sensoroid.controller;

import com.controller.sensoroid.R;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class AccelerometerActivity extends BaseActivity implements SensorEventListener
{
	private static final String TAG = "SpeedometerActivity";
	private SensorManager sensorManager;
	private Sensor accSensor;
	boolean initialFlag;
	private float oldx, oldy, oldz;
	private float approxNoise = 1f; //Approximate noise to be subtracted
	private DrawView drawView;
	private static int width;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelero);

        DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		
		initialFlag = false;
		try 
		{
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onCreate : "+e.toString());
		}
	}

	protected void onResume()
	{
		super.onResume();
		sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void onPause() 
	{
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
		//not implemented. of no use for now
	}

	@Override
	public void onSensorChanged(SensorEvent se) 
	{
		try 
		{
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			
			if(!initialFlag)
			{
				initialFlag = true;
				oldx = x;
				oldy = y;
				oldz = z;
			}
			else
			{
				float changex = Math.abs(oldx - x);
				float changey = Math.abs(oldy - y);
				float changez = Math.abs(oldz - z);
				if(changex < approxNoise) changex = 0.0f;
				if(changey < approxNoise) changey = 0.0f;
				if(changez < approxNoise) changez = 0.0f;
				oldx = x;
				oldy = y;
				oldz = z;

				drawView = new DrawView(this, changex, changey, changez, width);
		        drawView.setBackgroundResource(R.drawable.bg);

		        //for some reason this swipe handler does not work, TODO : Fix this later
				swipeHandler(getIntent().putExtra("parent", "accelero"), drawView, this);
				
		        setContentView(drawView);
			}
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onSensorChanged : "+e.toString());
		} 
	}
}
