package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by andre on 21.05.2015.
 * A class to draw only the accelerometer data and magnitude
 */
public class PlotView extends SensorView {
    public PlotView(Context context, AttributeSet attr) {
        super(context,attr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxisEnd = this.width - this.padding;
        super.onDraw(canvas);
        if(sensorDatas.size() > sumPlots) {
            for (int i = 1; i < sumPlots-1; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,d1.getX(),d2.getX(),canvas,Color.RED);
                drawSensorLine(i,d1.getY(),d2.getY(),canvas,Color.GREEN);
                drawSensorLine(i,d1.getZ(),d2.getZ(),canvas,Color.BLUE);
                drawSensorLine(i,calcMagnitude(d1),calcMagnitude(d2),canvas,Color.WHITE);
            }
        }
        else if(this.sensorDatas.size() < sumPlots && this.sensorDatas.size() > 2) {
            for(int i = 1; i < this.sensorDatas.size(); ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,d1.getX(),d2.getX(),canvas,Color.RED);
                drawSensorLine(i,d1.getY(),d2.getY(),canvas,Color.GREEN);
                drawSensorLine(i,d1.getZ(),d2.getZ(),canvas,Color.BLUE);
                drawSensorLine(i,calcMagnitude(d1),calcMagnitude(d2),canvas,Color.WHITE);
            }
        }
    }
}
