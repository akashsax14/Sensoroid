package com.sensoroid.controller;

import com.controller.sensoroid.R;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AmbientLightActivity extends BaseActivity implements SensorEventListener
{
	private static final String TAG = "AmbientLightActivity";
	private SensorManager sensorManager;
	private Sensor accSensor;
	
	private int brightness;
	private ContentResolver cResolver;
	private Window window;
	private ImageView bulbIv;
	private int width;
	private float maxLux = 255;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light);
		
		try 
		{
			swipeHandler(getIntent().putExtra("parent", "ambient"), findViewById(R.id.lightParentLayout), this);
			
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
			
			LinearLayout parentL = (LinearLayout)findViewById(R.id.lightParentLayout);
			parentL.setBackgroundResource(R.drawable.bg);
			
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			width = displaymetrics.widthPixels;
			
			TextView headerTv = (TextView) findViewById(R.id.lightHeader);
			headerTv.setTextSize(width/20);
			
			bulbIv = new ImageView(this);
			bulbIv.setBackgroundResource(R.drawable.bulb_color);
			bulbIv.setAlpha(0f); //0 - transparent, 1 - opaque
			
			LinearLayout lIv = (LinearLayout) findViewById(R.id.ivLayout);
			lIv.addView(bulbIv);
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		//not implemented. of no use for now
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		try 
		{
			float lux = event.values[0];//Light sensitivity (0-1)
			maxLux = maxLux < lux ? lux : maxLux;
			float alpha = (lux * 255 / maxLux);
			bulbIv.setAlpha(1-alpha/255);//set bulb transparency
			
			cResolver = getContentResolver();
			window = getWindow();
			Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
			Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
			LayoutParams wparams = window.getAttributes();
			wparams.screenBrightness = brightness / lux > 1 ? 1: brightness / lux;
            window.setAttributes(wparams);

			TextView luxTv = (TextView) findViewById(R.id.lightText);
            luxTv.setText(lux + "");
            
            TextView brightnessTv = (TextView) findViewById(R.id.brightnessText);
            brightnessTv.setText(wparams.screenBrightness + "");
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onSensorChanged : "+e.toString());
		} 
	}
}
