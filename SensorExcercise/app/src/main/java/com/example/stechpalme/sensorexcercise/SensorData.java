package com.example.stechpalme.sensorexcercise;

/**
 * Created by andre on 21.05.2015.
 */
public class SensorData {
    private float x;
    private float y;
    private float z;
    public SensorData() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    public SensorData(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
    public float getZ() {
        return this.z;
    }
}
