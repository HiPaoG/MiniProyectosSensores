package com.example.paola.miniproyectosensores;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import org.w3c.dom.Text;

public class Brujula extends AppCompatActivity implements SensorEventListener {

    private ImageView imgCompass;
    private float currentDegree = 0f;

    float degree;
    float[] mGravity, mGeomagnetic;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
        }

        if((mGravity != null) && (mGeomagnetic != null)) {
            float RotationMatrix[] = new float[16];
            boolean success = SensorManager.getRotationMatrix(RotationMatrix, null, mGravity, mGeomagnetic);
            if(success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(RotationMatrix, orientation);
                degree = orientation[0] * (180 / (float) Math.PI);
            }
        }

        RotateAnimation ra = new RotateAnimation(currentDegree, degree, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(1000);
        ra.setFillAfter(true);
        imgCompass.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brujula);

        imgCompass = (ImageView)findViewById(R.id.ivCompass);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mGravity = null;
        mGeomagnetic = null;

        sensorManager.registerListener(this, mag, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, acc, sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void regresar(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("Ventana",0);
        startActivity(i);
        this.finish();
    }
}
