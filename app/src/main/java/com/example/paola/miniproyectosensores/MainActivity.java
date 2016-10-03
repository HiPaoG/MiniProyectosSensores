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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    RelativeLayout layout;
    Lienzo papel;
    int ancho, alto;
    float x = 0, y = 0, z = 0;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = (event.values[1]*1000)+(ancho/2);
        y = (event.values[0]*1000)+(alto/2);
        z = event.values[2];
        TextView ax = (TextView)findViewById(R.id.tv1);
        ax.setText("x: "+x+" y: "+y+" z: "+z);
        papel.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gyro, sensorManager.SENSOR_DELAY_FASTEST);

        layout = (RelativeLayout)findViewById(R.id.l1);
        papel = new Lienzo(this);
        layout.addView(papel);

        new Asincrono().execute();
    }

    class Asincrono extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            while (true)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            papel.invalidate();
        }
    }

    class Lienzo extends View {
        public Lienzo(Context context) { super(context); }

        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(200, 230, 201);
            ancho = canvas.getWidth();
            alto = canvas.getHeight();

            Paint pincel = new Paint();
            pincel.setARGB(55, 34, 58, 60);
            pincel.setStrokeWidth(5);
            canvas.drawCircle(x, y, 20, pincel);
        }
    }

    public void regresar(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("Ventana",0);
        startActivity(i);
        this.finish();
    }
}
