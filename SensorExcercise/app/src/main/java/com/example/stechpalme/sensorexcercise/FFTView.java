package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by andre on 5/22/15.
 */
public class FFTView extends SensorView {
    FFT myfft;
    double[] x = new double[sumPlots];
    double[] y = new double[sumPlots];
    public FFTView(Context context, AttributeSet attr) {
        super(context, attr);
        myfft = new FFT(sumPlots);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxisEnd = this.width - this.padding;
        super.onDraw(canvas);
        if(sensorDatas.size() > sumPlots) {
            x = new double[sumPlots];
            y = new double[sumPlots];
            for (int i = 0; i < sumPlots; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                x[i] = (double) calcMagnitude(d1);
            }
            Arrays.fill(y, 0.0d);

            myfft.fft(x, y);

            for (int i = 0; i < sumPlots/2; ++i) {
                y[i] = Math.sqrt(Math.pow(x[i], 2) + Math.pow(y[i], 2));
            }
            for (int i = 1; i < (sumPlots/2) - 1; ++i) {
                float l = (float) y[i];
                float r = (float) y[i + 1];
                drawSensorLine(i, l, r, canvas, Color.YELLOW);
            }
            //showActualActivity();
        }
    }
    @Override
    public void removeData() {
        super.removeData();
        myfft = new FFT(sumPlots);
    }
    @Override
    protected void drawSensorLine(int i, float val1, float val2, Canvas canvas, int color) {
        float posX1 = (i * (this.width / (sumPlots/2)));
        float posX2 = ((i + 1) * (this.width/(sumPlots/2)));

        float posY1 = this.height - (height/100)*val1;
        float posY2 = this.height - (height/100)*val2;

        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(2.0f);
        canvas.drawLine(posX1, posY1, posX2, posY2, p);
    }
    private double getAvgMag() {
        double sum = 0.0d;
        //we have to go over only the half of the values because of the mirrored part of fft
        for(int i=0; i<sumPlots/2; ++i) {
            sum += y[i];
        }
        return Math.round((sum/(sumPlots/2))*1000)/1000.0;
    }
    private double getMaxFreq() {
        double max = 0;
        for(int i=0; i<sumPlots/2; ++i) {
            if(y[i] > max) max = y[i];
        }
        return Math.round(max*1000)/1000.0;
    }
    public String showActualActivity(TextView input) {
        // chillmode:
        // avg < 25
        // max < 660

        //running:
        //avg ?
        //max ?

        //walking:
        //avg ?
        //max ?
        if(sensorDatas.size() > sumPlots) {

            input.setText(getAvgMag()
                    + "  -  "
                    + getMaxFreq());
        }
        return "";
    }
}
