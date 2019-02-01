package com.example.vege.quizgame;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vege.quizgame.Fragments.GameFragment;
import com.example.vege.quizgame.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView mCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinates = findViewById(R.id.coordinates);

        //sensor setup
        sensorConfig();

        //setting initial screen (home fragment)
        setFragment();

    }

    @Override
    protected void onPause() {
        super.onPause();

        stopSensor();

    }

    @Override
    protected void onResume() {
        super.onResume();

        startSensor();

    }

    /**SENSOR FUNCTIONS**/
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:

                //coordinates
                float x = event.values[0];
                float y = event.values[1];

                //coordinates prints in textview
                mCoordinates.setText("x: " + String.valueOf(x) + "\n" +
                        "y: " + String.valueOf(y));

                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void setFragment() {
        //activity will start with home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();

    }

    private void sensorConfig(){
        mSensorManager = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startSensor();

    }

    private void stopSensor() {
        mSensorManager.unregisterListener(this);
    }

    private void startSensor() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
