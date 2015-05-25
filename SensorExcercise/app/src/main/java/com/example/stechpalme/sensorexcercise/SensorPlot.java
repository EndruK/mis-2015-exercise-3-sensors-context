package com.example.stechpalme.sensorexcercise;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class SensorPlot extends Activity implements SensorEventListener, SeekBar.OnSeekBarChangeListener {
    SensorManager sManager;
    PlotView myPlotView;
    FFTView myFFTView;
    SeekBar mySeekBar;
    SeekBar fftSeekBar;
    TextView myTextView;
    NotificationCompat.Builder myBuilder;
    NotificationManager myNotificationManager;
    String movementAction = "";
    int mID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_plot);
        //initialize everything
        myPlotView = (PlotView) findViewById(R.id.plot1);
        myPlotView.setBackgroundColor(Color.BLACK);
        myFFTView = (FFTView) findViewById(R.id.plot2);
        myFFTView.setBackgroundColor(Color.BLACK);
        mySeekBar = (SeekBar) findViewById(R.id.seekBar);
        mySeekBar.setOnSeekBarChangeListener(this);
        fftSeekBar = (SeekBar) findViewById(R.id.seekBar2);
        fftSeekBar.setOnSeekBarChangeListener(this);
        myTextView = (TextView) findViewById(R.id.textview1);

        //notification stuff
        myBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_maps_directions_walk)
                .setContentTitle("Sensor Excercise")
                .setContentText("test");
        Intent myIntent = new Intent(this,SensorPlot.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SensorPlot.class);
        stackBuilder.addNextIntent(myIntent);
        PendingIntent myPendingIntent
                = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        myBuilder.setContentIntent(myPendingIntent);
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(mID,myBuilder.build());

        //sensor stuff
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),100000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor_plot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        //we only want the accelerometer data
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            SensorData data = new SensorData(event.values[0],event.values[1],event.values[2]);
            this.myPlotView.addData(data); //add values to Plot View
            myPlotView.invalidate(); //redraw
            this.myFFTView.addData(data); //add values to FFT View
            myFFTView.invalidate(); //redraw
            //change the notification based on the results of the fft
            if(!movementAction.equals(myFFTView.showActualActivity(myTextView))
                    && !myFFTView.showActualActivity(myTextView).equals("")) {
                movementAction = myFFTView.showActualActivity(myTextView);
                myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                myBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_maps_directions_walk)
                        .setContentTitle("Sensor Excercise")
                        .setContentText(movementAction);
                myNotificationManager.notify(mID, myBuilder.build());
            }
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //upper seekbar
        if(seekBar.getId() == mySeekBar.getId()) {
            int progress = seekBar.getProgress();
            //just re-register the listener with a different sampling rate
            sManager.unregisterListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            myPlotView.removeData();
            myPlotView.invalidate();
            myFFTView.removeData();
            myFFTView.invalidate();
            sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), progress * 1000);
        }
        //lower seekbar
        else if(seekBar.getId() == fftSeekBar.getId()) {
            int progress = (int)Math.round(seekBar.getProgress()/15);
            //change the array size
            myPlotView.sumPlots = (int)Math.pow(2,progress+2);
            myFFTView.sumPlots = (int)Math.pow(2,progress+2);
            myPlotView.removeData();
            myFFTView.removeData();
            myPlotView.invalidate();
            myFFTView.invalidate();
        }
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

}
