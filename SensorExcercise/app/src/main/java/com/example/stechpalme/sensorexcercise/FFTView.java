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
    FFT myfft;
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
            double[] x = new double[sumPlots];
            double[] y = new double[sumPlots];
            for (int i = 0; i < sumPlots; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                x[i] = (double) calcMagnitude(d1);
            }
            Arrays.fill(y, 0.0d);

            myfft.fft(x, y);

            for (int i = 0; i < sumPlots; ++i) {
                y[i] = Math.sqrt(Math.pow(x[i], 2) + Math.pow(y[i], 2));
            }
            for (int i = 1; i < sumPlots - 1; ++i) {
                float l = (float) y[i];
                float r = (float) y[i + 1];
                drawSensorLine(i, l, r, canvas, Color.YELLOW);
            }
        }
    }
    @Override
    public void removeData() {
        super.removeData();
        myfft = new FFT(sumPlots);
    }
    @Override
    protected void drawSensorLine(int i, float val1, float val2, Canvas canvas, int color) {
        float posX1 = (i * ((this.width) / sumPlots));
        float posX2 = ((i + 1) * ((this.width)/sumPlots));

        float posY1 = this.height - (height/100)*val1;
        float posY2 = this.height - (height/100)*val2;

        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(2.0f);
        canvas.drawLine(posX1, posY1, posX2, posY2, p);
    }
}
