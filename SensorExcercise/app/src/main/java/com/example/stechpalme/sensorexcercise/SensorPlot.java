package com.example.stechpalme.sensorexcercise;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

public class SensorPlot extends Activity implements SensorEventListener, SeekBar.OnSeekBarChangeListener {
    SensorManager sManager;
    PlotView myPlotView;
    SeekBar mySeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_plot);
        myPlotView = (PlotView) findViewById(R.id.plot1);
        myPlotView.setBackgroundColor(Color.BLACK);
        mySeekBar = (SeekBar) findViewById(R.id.seekBar);
        mySeekBar.setOnSeekBarChangeListener(this);
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
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            SensorData data = new SensorData(event.values[0],event.values[1],event.values[2]);
            this.myPlotView.addData(data);
            myPlotView.invalidate();
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // 0 - 100
        sManager.unregisterListener(this,sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),progress*1000);
    }

}
