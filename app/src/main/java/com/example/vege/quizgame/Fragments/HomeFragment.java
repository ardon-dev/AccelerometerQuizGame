package com.example.vege.quizgame.Fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vege.quizgame.MainActivity;
import com.example.vege.quizgame.R;

public class HomeFragment extends Fragment implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorConfig();
    }

    @Override
    public void onResume() {
        super.onResume();

        getViews();


    }

    private void getViews() {



    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:

                //coordinates
                float x = event.values[0];
                float y = event.values[1];

                //when device turns up, game start
                if (x < 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            new GameFragment()).commit();
                    mSensorManager.unregisterListener(this);
                }

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void sensorConfig(){

        mSensorManager = (SensorManager) getContext().getSystemService(getContext().SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
