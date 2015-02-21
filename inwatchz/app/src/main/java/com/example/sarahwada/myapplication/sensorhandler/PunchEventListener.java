package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class PunchEventListener extends ActionEventListener {

    float mLastAccelWithGrav = 0.00f;
    float mAccelWithGrav = SensorManager.GRAVITY_EARTH;
    float mAccelNoGrav = SensorManager.GRAVITY_EARTH;

    /* Pull Threshold */
    final float punchThreshold = 7.0f;

    public PunchEventListener(SensorManager sensorManager, Context context) {
        super(sensorManager, context);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float[] values = event.values;

            /* Detected movement */
            float x = values[0];
            float y = values[1];

            boolean punching = (x < 0);

            mLastAccelWithGrav = mAccelWithGrav;
            mAccelWithGrav = android.util.FloatMath.sqrt(1.9f*x*x + 0.2f*y*y);
            float delta = mAccelWithGrav - mLastAccelWithGrav;
            mAccelNoGrav = mAccelNoGrav * 0.9f + delta; //low-pass filter


            if (mAccelNoGrav > punchThreshold && punching) {
                executePunchAction(mAccelNoGrav);
            }
        }
    }

    private void executePunchAction(float magnitude) {
        Toast.makeText(context, "ow: " + magnitude, Toast.LENGTH_SHORT).show();
        this.success = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
