package com.example.vege.quizgame.Fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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
    private TextView mQuestion, mAnswer1, mAnswer2, mGameFinished;
    public static int counter;
    private RelativeLayout mAnswerBG1, mAnswerBG2;

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
        mAnswerBG1 = getActivity().findViewById(R.id.answerBackground1);
        mAnswerBG2 = getActivity().findViewById(R.id.answerBackground2);
        mGameFinished = getActivity().findViewById(R.id.gameFinishedText);

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

        //sensor stops
        stopSensor();

    }

    @Override
    public void onResume() {
        super.onResume();

        //sensor continue working
        sensorStart();

        //the game will ends when the questions are over
        if (counter < MainActivity.db.questionDao().getAllQuestion().size()) {
            gameStart();

        } else {
            gameFinish();

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
                        validateAnswerOne();
                        stopSensor();
                        switchFragment();
                        break;
                    case 2:
                        validateAnswerTwo();
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

    private void gameStart() {
        //counter will increment to show the next question when the fragment be restarted
        counter++;
        //set question and anwser to views
        mQuestion.setText(MainActivity.db.questionDao().getSingleQuestion(counter));
        mAnswer1.setText(MainActivity.db.questionDao().getSingleAnswerOne(counter));
        mAnswer2.setText(MainActivity.db.questionDao().getSingleAnswerTwo(counter));

    }

    private void gameFinish() {

        //HIDE VIEWS
        mAnswer1.setVisibility(View.INVISIBLE);
        mAnswerBG1.setVisibility(View.INVISIBLE);
        mAnswer2.setVisibility(View.INVISIBLE);
        mAnswerBG2.setVisibility(View.INVISIBLE);
        mQuestion.setVisibility(View.INVISIBLE);

        //SEE VIEW
        mGameFinished.setVisibility(View.VISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //game finishes
                stopSensor();
                //question counter restarts
                counter = 0;
                //activity close
                getActivity().finish();
            }
        }, 1000);

    }

    private void switchFragment() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //fragment will be loaded again after 2 seconds
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.gameContainer,
                        new GameFragment()).commit();

            }
        }, 2000);

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

    private void validateAnswerOne() {

        String rightAnswer = MainActivity.db.questionDao().getSingleAnswerRight(counter);
        String answerOne = MainActivity.db.questionDao().getSingleAnswerOne(counter);

        if (!answerOne.equals(rightAnswer)) {
            onWrongSound();
            mAnswerBG1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            mAnswerBG2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            onRightSound();
            mAnswerBG1.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            mAnswerBG2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            mAnswer1.setTextSize(48);
        }


    }

    private void validateAnswerTwo() {

        String rightAnswer = MainActivity.db.questionDao().getSingleAnswerRight(counter);
        String answerTwo = MainActivity.db.questionDao().getSingleAnswerTwo(counter);

        if (!answerTwo.equals(rightAnswer)) {
            onWrongSound();
            mAnswerBG1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            mAnswerBG2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            onRightSound();
            mAnswerBG1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            mAnswerBG2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            mAnswer2.setTextSize(48);
        }


    }

    private void onRightSound() {
        MediaPlayer correct = MediaPlayer.create(getContext(), R.raw.correct_answer);
        correct.start();
    }

    private void onWrongSound() {
        MediaPlayer wrong = MediaPlayer.create(getContext(), R.raw.wrong_answer);
        wrong.start();
    }

}
