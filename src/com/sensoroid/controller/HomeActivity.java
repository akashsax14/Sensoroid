package com.sensoroid.controller;

import com.controller.sensoroid.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {

	private static final String TAG = "HomeActivity";
	private int width;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		context = this;
		try 
		{
			swipeHandler(getIntent().putExtra("parent", "home"), findViewById(R.id.homeParentLayout), this);
			initializeHomeScreen();
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in onCreate : "+e.toString());
		} 
	}
	
	public void initializeHomeScreen()
	{
		try 
		{	
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			width = displaymetrics.widthPixels;
			
			LinearLayout parentL = (LinearLayout)findViewById(R.id.homeParentLayout);
			parentL.setBackgroundResource(R.drawable.bg);
			
			/**** Heading Layout ****/
			LinearLayout headingL = new LinearLayout(this);
			LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			headingL.setLayoutParams(lparams);
			headingL.setGravity(Gravity.CENTER_HORIZONTAL);
			headingL.setOrientation(LinearLayout.VERTICAL);
			
			TextView headingTv = new TextView(this);
			headingTv.setTextSize(width/20);
			headingTv.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
			headingTv.setGravity(Gravity.CENTER_HORIZONTAL);
			headingTv.setText("Sensoroid");

			TextView info = new TextView(this);
			info.setGravity(Gravity.CENTER_HORIZONTAL);
			info.setText("(Swipe in any direction)");
			
			headingL.addView(headingTv);
			headingL.addView(info);
			
			parentL.addView(headingL);
			
			/**** Up Arrow Layout ****/
			LinearLayout upArrowLP = new LinearLayout(this);
			upArrowLP.setLayoutParams(lparams);
			upArrowLP.setOrientation(LinearLayout.HORIZONTAL);
			
			LinearLayout upArrowL = new LinearLayout(this);
			upArrowL.setLayoutParams(lparams);
			upArrowL.setOrientation(LinearLayout.VERTICAL);
			upArrowL.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ImageView upArrow = new ImageView(this);
			LayoutParams iparams = new LayoutParams(width/3, width/3);
			upArrow.setLayoutParams(iparams);
			upArrow.setBackgroundResource(R.drawable.up);
			
			TextView upTv = new TextView(this);
			upTv.setTextSize(width/50);
			upTv.setTypeface(Typeface.MONOSPACE);
			upTv.setGravity(Gravity.CENTER_HORIZONTAL);
			upTv.setText("Accelerometer");
			
			upArrowL.addView(upArrow);
			upArrowL.addView(upTv);
			upArrowLP.addView(upArrowL);
			parentL.addView(upArrowLP);

			/**** Left-Right Arrow Layout ****/
			LinearLayout lrArrowLP = new LinearLayout(this);
			upArrowLP.setLayoutParams(lparams);
			upArrowLP.setOrientation(LinearLayout.HORIZONTAL);
			
			/**Left Arrow**/
			LinearLayout leftArrowL = new LinearLayout(this);
			LayoutParams lrparams = new LayoutParams(width/2, LayoutParams.WRAP_CONTENT);
			leftArrowL.setLayoutParams(lrparams);
			leftArrowL.setOrientation(LinearLayout.VERTICAL);
			leftArrowL.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ImageView leftArrow = new ImageView(this);
			leftArrow.setLayoutParams(iparams);
			leftArrow.setBackgroundResource(R.drawable.left);
			
			TextView leftTv = new TextView(this);
			leftTv.setTextSize(width/50);
			leftTv.setTypeface(Typeface.MONOSPACE);
			leftTv.setGravity(Gravity.CENTER_HORIZONTAL);
			leftTv.setText("Proximity Sensor");
			
			leftArrowL.addView(leftArrow);
			leftArrowL.addView(leftTv);
			
			/**Right Arrow**/
			LinearLayout rightArrowL = new LinearLayout(this);
			rightArrowL.setLayoutParams(lrparams);
			rightArrowL.setOrientation(LinearLayout.VERTICAL);
			rightArrowL.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ImageView rightArrow = new ImageView(this);
			rightArrow.setLayoutParams(iparams);
			rightArrow.setBackgroundResource(R.drawable.right1);
			
			TextView rightTv = new TextView(this);
			rightTv.setTextSize(width/50);
			rightTv.setTypeface(Typeface.MONOSPACE);
			rightTv.setGravity(Gravity.CENTER_HORIZONTAL);
			rightTv.setText("Ambient Light\nSensor");
			
			rightArrowL.addView(rightArrow);
			rightArrowL.addView(rightTv);
			
			lrArrowLP.addView(leftArrowL);
			lrArrowLP.addView(rightArrowL);
			parentL.addView(lrArrowLP);
			
			/**** Down Arrow Layout ****/
			LinearLayout downArrowLP = new LinearLayout(this);
			downArrowLP.setLayoutParams(lparams);
			downArrowLP.setOrientation(LinearLayout.HORIZONTAL);
			
			LinearLayout downArrowL = new LinearLayout(this);
			downArrowL.setLayoutParams(lparams);
			downArrowL.setOrientation(LinearLayout.VERTICAL);
			downArrowL.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ImageView downArrow = new ImageView(this);
			downArrow.setLayoutParams(iparams);
			downArrow.setBackgroundResource(R.drawable.down1);
			
			TextView downTv = new TextView(this);
			downTv.setTextSize(width/50);
			downTv.setTypeface(Typeface.MONOSPACE);
			downTv.setGravity(Gravity.CENTER_HORIZONTAL);
			downTv.setText("Magnetometer");
			
			downArrowL.addView(downArrow);
			downArrowL.addView(downTv);
			downArrowLP.addView(downArrowL);
			parentL.addView(downArrowLP);
			
			/**** About Layout ****/
			LinearLayout aboutL = new LinearLayout(this);
			aboutL.setLayoutParams(lparams);
			aboutL.setOrientation(LinearLayout.VERTICAL);
			aboutL.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ImageView aboutIv = new ImageView(this);
			LayoutParams aboutParams = new LayoutParams(100,100);
			aboutParams.setMargins(0, 20, 0, 0);
			aboutIv.setLayoutParams(aboutParams);
			aboutIv.setBackgroundResource(android.R.drawable.ic_dialog_info);
			aboutIv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, InfoActivity.class);
					startActivity(i);
				}
			});
			TextView aboutTv = new TextView(this);
			aboutTv.setText("Info");
			aboutTv.setGravity(Gravity.CENTER_HORIZONTAL);
			
			aboutL.addView(aboutIv);
			aboutL.addView(aboutTv);
			parentL.addView(aboutL);
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Error in initializeHomeScreen : "+e.toString());
		} 
	}
	
	public int dp(int dps)
	{
		final float scale = this.getResources().getDisplayMetrics().density;
		int pixels = (int) (dps * scale + 0.5f);
		return pixels;
	}
	public float pixel(float dp)
	{
		final float scale = this.getResources().getDisplayMetrics().densityDpi;
	    float px = dp * (scale / 160f);
	    return px;
	}
}
