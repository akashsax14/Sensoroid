package com.sensoroid.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class DrawView extends View 
{
    Paint paint = new Paint(), paint1 = new Paint(), paint2 = new Paint(), paint3 = new Paint();
    private float xaxis, yaxis, zaxis;
    private int width;
    
    public DrawView(Context context, float x, float y, float z, int width) 
    {
        super(context);
        xaxis = x;
        yaxis = y;
        zaxis = z;
        this.width = width;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(width/10);
        paint.setTextSize(width/10);
        paint.setTypeface(Typeface.MONOSPACE);
        
        paint1.setTextSize(width/10);
        paint1.setColor(Color.YELLOW);
        
        paint2.setTextSize(width/13);
        paint2.setTypeface(Typeface.MONOSPACE);
        
        paint3.setTextSize(width/25);
    }

    @Override
    public void onDraw(Canvas canvas) 
    {
    	int width3 = width/3;
    	
    	float xend = xaxis * 100 + width3 > width ? width - 50 : xaxis * 100 + width3;
    	float yend = yaxis * 100 + width3 > width ? width - 50 : yaxis * 100 + width3;
    	float zend = zaxis * 100 + width3 > width ? width - 50 : zaxis * 100 + width3;
    	
    	canvas.drawText("Accelerometer", 10, 100, paint);
    	canvas.drawText("(Press back to return)", 10, 150, paint3);
    	
    	canvas.drawText("X-Axis", 10, 300, paint2);
    	canvas.drawText("Y-Axis", 10, 500, paint2);
    	canvas.drawText("Z-Axis", 10, 700, paint2);
    	
        canvas.drawLine(width3, 260, xend, 260, paint);
    	canvas.drawText(xaxis + "", width3, 300, paint1);
    	
        canvas.drawLine(width3, 460, yend, 460, paint);
    	canvas.drawText(yaxis + "", width3, 500, paint1);
    	
        canvas.drawLine(width3, 660, zend, 660, paint);
    	canvas.drawText(zaxis + "", width3, 700, paint1);
    }

}