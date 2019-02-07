package com.example.vege.quizgame;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vege.quizgame.DataBase.AllQuestions;
import com.example.vege.quizgame.DataBase.AppDataBase;
import com.example.vege.quizgame.DataBase.Question;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView mCoordinates, mPlay;
    public static AppDataBase db;
    private static MediaPlayer homeMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mCoordinates = findViewById(R.id.coordinates);
        mPlay = findViewById(R.id.buttonPlay);

        //music starts
        homeMusic = MediaPlayer.create(this, R.raw.home_music);
        homeMusic.start();

        //database build
        db = Room.databaseBuilder(this, AppDataBase.class, "question_db")
                .allowMainThreadQueries().build();

        //add all questions to database
        if (db.questionDao().getAllQuestion().isEmpty() == true) {
            insertQuestionToDB();

        }

        //sensor setup
        sensorConfig();

        //intent to game activity listener
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button sound
                MediaPlayer.create(MainActivity.this, R.raw.start).start();
                //intent to game activity
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                stopSensor();
            }
        });

        //list onclick button
        showQuestions();

    }

    @Override
    protected void onPause() {
        stopSensor();
        homeMusic.pause();
        super.onPause();



    }

    @Override
    protected void onResume() {
        super.onResume();

        startSensor();
        homeMusic.start();

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

    private void showQuestions() {

        ImageView mShowQuestionsButton = findViewById(R.id.buttomShowQuestions);
        mShowQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void insertQuestionToDB() {
        for (Question q : AllQuestions.insertQuestions()) {
            db.questionDao().insertAllQuestion(new Question(q.getQuestion_question(),
                    q.getQuestion_answer_1(),
                    q.getQuestion_answer_2(),
                    q.getQuestion_answer_right()));

        }

    }


}
