package com.example.paola.miniproyectosensores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Proximidad extends AppCompatActivity implements SensorEventListener{

    Float x;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = Float.parseFloat(String.valueOf(event.values[0]));
        EditText et = (EditText)findViewById(R.id.et1);
        TextView tv = (TextView)findViewById(R.id.textView4);
        tv.setText(et.getText());
        if(x <= 20) {
            tv.setTextSize(10);
        } else if(x > 20 && x <= 25) {
            tv.setTextSize(16);
        } else if(x > 25 && x <= 30) {
            tv.setTextSize(22);
        } else if(x > 30 && x <= 40) {
            tv.setTextSize(28);
        } else if(x > 40) {
            tv.setTextSize(36);
        }
        TextView tv1 = (TextView) findViewById(R.id.textView3);
        tv1.setText("Proximidad: "+x);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "8vo. Semestre", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor prox = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, prox, sensorManager.SENSOR_DELAY_FASTEST);
    }

    public void regresar(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("Ventana",0);
        startActivity(i);
        this.finish();
    }

}
