package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.ImageView;

import com.example.sarahwada.myapplication.R;

/**
 *
 */
public class PushEventListener extends ActionEventListener {

    float mLastAccelWithGrav = 0.00f;
    float mAccelWithGrav = SensorManager.GRAVITY_EARTH;
    float mAccelNoGrav = SensorManager.GRAVITY_EARTH;

    /* Push Threshold */
    final float pushThreshold = 8.0f;

    public PushEventListener(SensorManager sensorManager, Context context) {
        super(sensorManager, context);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) && !this.success) {

            float[] values = event.values;

            /* Detected movement */
            float x = values[0];
            float y = values[1];

            boolean pushing = (y > 0);

            mLastAccelWithGrav = mAccelWithGrav;
            mAccelWithGrav = android.util.FloatMath.sqrt(0.2f*x*x + 1.9f*y*y);
            float delta = mAccelWithGrav - mLastAccelWithGrav;
            mAccelNoGrav = mAccelNoGrav * 0.9f + delta; //low-pass filter


            if (mAccelNoGrav > pushThreshold && pushing) {
                executePushAction(mAccelNoGrav);
            }
        }
    }

    private void executePushAction(float magnitude) {
        this.success = true;
        this.context.setIsMotionCorrect(true);
        ImageView image = (ImageView) this.context.findViewById(R.id.image);
        image.setImageResource(R.drawable.push_complete);
        MediaPlayer.create(this.context, R.raw.push_effect).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
