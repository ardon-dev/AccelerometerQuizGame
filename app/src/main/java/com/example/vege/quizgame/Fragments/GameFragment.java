package com.example.vege.quizgame.Fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vege.quizgame.MainActivity;
import com.example.vege.quizgame.R;

public class GameFragment extends Fragment implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView mQuestion, mAnswer1, mAnswer2;
    private RelativeLayout mBackgroundFull;
    private static int counter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mQuestion = getActivity().findViewById(R.id.viewQuestion);
        mAnswer1 = getActivity().findViewById(R.id.buttonAnswer1);
        mAnswer2 = getActivity().findViewById(R.id.buttonAnswer2);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sensor setup
        sensorConfig();

        //sensor initialize
        sensorStart();

    }

    @Override
    public void onPause() {
        super.onPause();
        stopSensor();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorStart();

        if (counter < MainActivity.db.questionDao().getAllQuestion().size()) {
            counter++;
            mQuestion.setText(MainActivity.db.questionDao().getSingleQuestion(counter));
            mAnswer1.setText(MainActivity.db.questionDao().getSingleAnswerOne(counter));
            mAnswer2.setText(MainActivity.db.questionDao().getSingleAnswerTwo(counter));

        } else {
            Toast.makeText(getContext(), "Juego acabado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:

                //coordinates
                float x = event.values[0];
                float y = event.values[1];

                int direction = (int)y;

                switch (direction) {
                    case -2:
                        Toast.makeText(getContext(),"Izquierda", Toast.LENGTH_SHORT).show();
                        stopSensor();
                        switchFragment();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Derecha", Toast.LENGTH_SHORT).show();
                        stopSensor();
                        switchFragment();
                        break;

                }

                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void switchFragment() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //fragment will be loaded again after 3 seconds
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new GameFragment()).commit();
                Toast.makeText(getContext(), "Switch", Toast.LENGTH_SHORT).show();

            }
        }, 3000);

    }

    private void sensorConfig(){

        mSensorManager = (SensorManager) getContext().getSystemService(getContext().SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private void sensorStart() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void stopSensor() {
        mSensorManager.unregisterListener(this);

    }

    private void setQuestion() {

    }



}
