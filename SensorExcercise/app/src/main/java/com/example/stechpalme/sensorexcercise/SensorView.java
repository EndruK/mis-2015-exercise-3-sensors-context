package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by andre on 5/22/15.
 */
public abstract class SensorView extends View {
    float width;
    float height;
    float padding = 50.0f;
    float originY;
    float xAxisEnd;
    int sumPlots = 64;
    ArrayList<SensorData> sensorDatas;
    public SensorView(Context context, AttributeSet attr) {
        super(context,attr);
        sensorDatas = new ArrayList<>();
    }
    protected void drawSensorLine(int i, float val1, float val2, Canvas canvas, int color) {
        /*
        | ----------------------------- |
        | 0 ........................ 50
         */
        float posX1 = (i * ((this.width) / sumPlots));
        float posX2 = ((i + 1) * ((this.width)/sumPlots));

        float posY1 = this.originY - ((height-2*padding)/100)*val1;
        float posY2 = this.originY - ((height-2*padding)/100)*val2;

        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(2.0f);
        canvas.drawLine(posX1, posY1, posX2, posY2, p);
    }
    public void addData(SensorData data) {
        sensorDatas.add(data);
        if(sensorDatas.size() > sumPlots+1) {
            sensorDatas.remove(0);
        }
    }
    public float calcMagnitude(SensorData d) {
        double x = (double)d.getX();
        double y = (double)d.getY();
        double z = (double)d.getZ();
        float magnitude = (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
        return magnitude;
    }
    public ArrayList<SensorData> getData() {
        return sensorDatas;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
