package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by andre on 21.05.2015.
 */
public class PlotView extends View {
    public PlotView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3f);
        canvas.drawLine(150,150,550,150,paint);
    }
}
