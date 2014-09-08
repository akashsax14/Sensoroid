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
import android.widget.LinearLayout;
import android.widget.TextView;

public class MagnetometerActivity extends BaseActivity implements SensorEventListener
{
	private static final String TAG = "MagnetometerActivity";
	private SensorManager sensorManager;
	private Sensor magSensor;
	
	private int width;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_magneto);
		
		try 
		{
			swipeHandler(getIntent().putExtra("parent", "magneto"), findViewById(R.id.magnetParentLayout), this);
			
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL);
			
			LinearLayout parentL = (LinearLayout)findViewById(R.id.magnetParentLayout);
			parentL.setBackgroundResource(R.drawable.bg);
			
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			width = displaymetrics.widthPixels;
			
			TextView headerTv = (TextView) findViewById(R.id.magnetHeader);
			headerTv.setTextSize(width/20);
			
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onCreate : "+e.toString());
		}
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		try 
		{
			float mag1 = event.values[0];
			float mag2 = event.values[1];
			float mag3 = event.values[2];
			
			TextView tv1 = (TextView) findViewById(R.id.magnetLabel1);
			TextView tv2 = (TextView) findViewById(R.id.magnetLabel2);
			TextView tv3 = (TextView) findViewById(R.id.magnetLabel3);
			tv1.setText("X-Axis : " + mag1);
			tv2.setText("Y-Axis : " + mag2);
			tv3.setText("Z-Axis : " + mag3);
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onSensorChanged : "+e.toString());
		} 
	}
}
