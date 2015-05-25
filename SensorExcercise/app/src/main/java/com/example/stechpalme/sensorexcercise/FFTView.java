package com.example.stechpalme.sensorexcercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by andre on 5/22/15.
 * A Class to draw the result of the FFT and to handle the 3 activities
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
        //wait for the array to be filled
        if(sensorDatas.size() > sumPlots) {
            x = new double[sumPlots];
            y = new double[sumPlots];
            //calc the magnitude of the sensor data
            for (int i = 0; i < sumPlots; ++i) {
                SensorData d1 = this.sensorDatas.get(this.sensorDatas.size() - 1 - i);
                x[i] = (double) calcMagnitude(d1);
            }
            Arrays.fill(y, 0.0d);
            //do the fft
            myfft.fft(x, y);
            //calc the magnitude of the resulting real and imaginary parts
            //since we have both parts real and imaginary in the result the plot is mirrored in the middle
            //so it is necessary to cut the result in the middle and go on with only the half
            for (int i = 0; i < sumPlots/2; ++i) {
                y[i] = Math.sqrt(Math.pow(x[i], 2) + Math.pow(y[i], 2));
            }
            //draw the lines
            for (int i = 1; i < (sumPlots/2) - 1; ++i) {
                float l = (float) y[i];
                float r = (float) y[i + 1];
                drawSensorLine(i, l, r, canvas, Color.YELLOW);
            }
        }
    }
    //clear all data
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
    //get the average frequency
    private double getAvgFreq() {
        double sum = 0.0d;
        //we have to go over only the half of the values because of the mirrored part of fft
        for(int i=0; i<sumPlots/2; ++i) {
            sum += y[i];
        }
        return Math.round((sum/(sumPlots/2))*1000)/1000.0;
    }
    //get the max frequency
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
        // max < 640~ not very accurate

        //running:
        //avg > 25 < 30
        //max 650~ not very accurate

        //walking:
        //avg > 30
        //max 690 - 710
        if(sensorDatas.size() > sumPlots) {

            input.setText(getAvgFreq()
                    + "  -  "
                    + getMaxFreq());
            double avg = getAvgFreq();
            double max = getMaxFreq();
            if(avg < 25) { //user is in chillmode
                input.setText(avg + " - " + max + " - chilling");
                return "you are in chillmode - keep relaxing";
            }
            else if(avg >= 25 && avg <= 31) { //user is walking
                input.setText(avg + " - " + max + " - walking");
                return "you're walking - easy";
            }
            else { //user is running
                input.setText(avg + " - " + max + " - running");
                return "you're running - calm down and relax";
            }
        }
        return "";
    }
}
