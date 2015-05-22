package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.Arrays;

/**
 * Created by andre on 5/22/15.
 */
public class FFTView extends SensorView {
    FFT fft;
    public FFTView(Context context, AttributeSet attr) {
        super(context, attr);
        fft = new FFT(sumPlots);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxisEnd = this.width - this.padding;
        super.onDraw(canvas);
        if(sensorDatas.size() > sumPlots) {
            double[] x = new double[sumPlots];
            double[] y = new double[sumPlots];
            for(int i = 0; i < sumPlots; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                x[i] = (double) calcMagnitude(d1);
            }
            Arrays.fill(y,0.0d);

            fft.fft(x,y);

            /*for (int i = 1; i < sumPlots+1; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,calcMagnitude(d1),calcMagnitude(d2),canvas,Color.WHITE);
            }*/
        }
        /*else if(this.sensorDatas.size() < sumPlots && this.sensorDatas.size() > 2) {
            for(int i = 1; i < this.sensorDatas.size(); ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - i);
                SensorData d2 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                drawSensorLine(i,calcMagnitude(d1),calcMagnitude(d2),canvas,Color.WHITE);
            }
        }*/
    }
}
