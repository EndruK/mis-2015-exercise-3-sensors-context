package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by andre on 21.05.2015.
 */
public class PlotView extends View {
    float width;
    float height;
    float padding = 50.0f;
    float originX = padding;
    float originY;
    float xAxisEnd;
    float yAxisEnd = padding;
    ArrayList<SensorData> sensorDatas;

    public PlotView(Context context, AttributeSet attr) {
        super(context,attr);
        sensorDatas = new ArrayList<>();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxisEnd = this.width - this.padding;
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1.0f);
        //canvas.drawLine(this.originX,this.originY, this.originX,this.yAxisEnd, paint);
        canvas.drawLine(this.originX,this.originY, this.xAxisEnd,this.originY, paint);
        if(sensorDatas.size() > 50) {
            for (int i = 1; i < 51; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,d1.getX(),d2.getX(),canvas,Color.RED);
                drawSensorLine(i,d1.getY(),d2.getY(),canvas,Color.GREEN);
                drawSensorLine(i,d1.getZ(),d2.getZ(),canvas,Color.BLUE);
            }
        }
        else if(this.sensorDatas.size() < 50 && this.sensorDatas.size() > 2) {
            for(int i = 1; i < this.sensorDatas.size(); ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,d1.getX(),d2.getX(),canvas,Color.RED);
                drawSensorLine(i,d1.getY(),d2.getY(),canvas,Color.GREEN);
                drawSensorLine(i,d1.getZ(),d2.getZ(),canvas,Color.BLUE);
            }
        }
    }

    private void drawSensorLine(int i, float val1, float val2, Canvas canvas, int color) {
        /*
        | ----------------------------- |
        | 0 ........................ 50
         */
        float posX1 = this.padding + (i * ((this.xAxisEnd - this.padding) / 50));
        float posX2 = this.padding + ((i + 1) * ((this.xAxisEnd - this.padding)/50));

        float posY1 = this.originY - ((height-2*padding)/100)*val1;
        float posY2 = this.originY - ((height-2*padding)/100)*val2;

        Paint p = new Paint();
        p.setColor(color);
        canvas.drawLine(posX1,posY1,posX2,posY2,p);
    }

    public void addData(SensorData data) {
        sensorDatas.add(data);
        if(sensorDatas.size() > 60) {
            sensorDatas.remove(0);
        }
    }
}
