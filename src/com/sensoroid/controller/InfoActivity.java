package com.sensoroid.controller;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.controller.sensoroid.R;

public class InfoActivity extends BaseActivity
{
	private static final String TAG = "InfoActivity";
	private SensorManager sensorManager;
	private int width;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		try 
		{
			swipeHandler(getIntent().putExtra("parent", "info"), findViewById(R.id.infoParentLayout), this);

			LinearLayout parentL = (LinearLayout)findViewById(R.id.infoParentLayout);
			parentL.setBackgroundResource(R.drawable.bg);
			
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			width = displaymetrics.widthPixels;
			
			TextView headerTv = (TextView) findViewById(R.id.infoHeader);
			headerTv.setTextSize(width/20);
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onCreate : "+e.toString());
		}
	}
	
	public void viewList(View v)
	{
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		String names = "";
		int i = 1;
		for(Sensor s : deviceSensors)
		{
			names = names + i + ") " + s.getName() + "\n\n";
			i++;
		}
		
		new AlertDialog.Builder(this)
	    .setTitle("Sensors")
	    .setMessage(names)
	    .setIcon(android.R.drawable.star_on)
	     .show();
	}
	
	public void viewAbout(View v)
	{
		String aboutMessage = "This application illustrates the use of a few sensors that are provided in most of the recent"
				+ " android devices. Sensors can be of many types and may differ in performance based on different manufacturers. "
				+ "The sensors illustrated in Sensoroid are : \n\n1) Accelerometer : Measures the acceleration force in m/s2 that "
				+ "is applied to a device on all three physical axes (x, y, and z), including the force of gravity.\n\n"
				+ "2) Light Sensor : Measures the ambient light level (illumination) in lx.\n\n3) Proximity Sensor : Measures the "
				+ "proximity of an object in cm relative to the view screen of a device.\n\n4) Magnetometer : Measures the magnetic "
				+ "field along the three axes\n\n5) Gestures : Swipe movements on the screen. \n\nDeveloped by :\nAkash Saxena\n"
				+ "New York University";
		new AlertDialog.Builder(this)
	    .setTitle("About")
	    .setMessage(aboutMessage)
	    .setIcon(android.R.drawable.ic_dialog_info)
	     .show();
	}
}
