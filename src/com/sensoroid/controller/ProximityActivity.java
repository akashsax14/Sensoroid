package com.sensoroid.controller;

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

import com.controller.sensoroid.R;

public class ProximityActivity extends BaseActivity implements SensorEventListener
{
	private static final String TAG = "ProximityActivity";
	private int width;
	private SensorManager sensorManager;
	private Sensor proxSensor;
	private LinearLayout near, far;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity);
		
		try 
		{
			swipeHandler(getIntent().putExtra("parent", "proximity"), findViewById(R.id.proximityParentLayout), this);
			
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
			sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
			
			swipeHandler(getIntent().putExtra("parent", "proximity"), findViewById(R.id.proximityParentLayout), this);

			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			width = displaymetrics.widthPixels;
			
			TextView headerTv = (TextView) findViewById(R.id.proximityHeader);
			headerTv.setTextSize(width/25);
			
			LinearLayout parentL = (LinearLayout)findViewById(R.id.proximityParentLayout);
			parentL.setBackgroundResource(R.drawable.bg);
			
			far = (LinearLayout) findViewById(R.id.farLayout);
			near = (LinearLayout) findViewById(R.id.nearLayout);
			far.setVisibility(LinearLayout.INVISIBLE);
			near.setVisibility(LinearLayout.INVISIBLE);
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onCreate : "+e.toString());
		} 
	}
	
	protected void onResume()
	{
		super.onResume();
		sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void onPause() 
	{
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		try 
		{
			float prox = event.values[0];
			
			if(prox<=0)
			{
				far.setVisibility(LinearLayout.INVISIBLE);
				near.setVisibility(LinearLayout.VISIBLE);
			}
			else
			{
				far.setVisibility(LinearLayout.VISIBLE);
				near.setVisibility(LinearLayout.INVISIBLE);
			}
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onSensorChanged : "+e.toString());
		} 
	}
}
