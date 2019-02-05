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

import com.example.vege.quizgame.DataBase.AppDataBase;
import com.example.vege.quizgame.DataBase.Question;
import com.example.vege.quizgame.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView mCoordinates;
    public static AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mCoordinates = findViewById(R.id.coordinates);

        //database build
        db = Room.databaseBuilder(this, AppDataBase.class, "question_db")
                .allowMainThreadQueries().build();

        db.questionDao().insertAllQuestion(new Question("¿Cuántos días tiene un año bisiesto?",
                "365 días",
                "366 días",
                "366 días"));

        db.questionDao().insertAllQuestion(new Question("¿Abraham Lincoln fue presidente de qué país?",
                "EEUU",
                "Inglaterra",
                "EEUU"));

        db.questionDao().insertAllQuestion(new Question("¿Cuál es la capital de China?",
                "Hong Kong",
                "Tokio",
                "Hong Kong"));

        db.questionDao().insertAllQuestion(new Question("¿Cómo se conoce a la escritura del Antiguo Egipto?",
                "Hebraica",
                "Jeroglíficos",
                "Jeroglíficos"));

        db.questionDao().insertAllQuestion(new Question("¿Dónde podemos encontrar la Estatua de la Libertad?",
                "Nueva York",
                "Los Ángeles",
                "Nueva York"));

        db.questionDao().insertAllQuestion(new Question("¿Qué hombre puso por primera vez el pie en la Luna?",
                "Lindbergh",
                "Armstrong",
                "Armstrong"));

        db.questionDao().insertAllQuestion(new Question("¿Cuántas estaciones tiene el año?",
                "Cuatro",
                "Cinco",
                "Cuatro"));

        db.questionDao().insertAllQuestion(new Question("¿A qué aparato pertenecen las venas?",
                "Circulatorio",
                "Digestivo",
                "Circulatorio"));

        db.questionDao().insertAllQuestion(new Question("¿Qué aparato se encarga de recoger oxígeno?",
                "Respiratorio",
                "Escretor",
                "Respiratorio"));

        db.questionDao().insertAllQuestion(new Question("¿Por qué sudamos cuando hace calor?",
                "Para evitar el cansancio",
                "Para regular la temperatura corporal",
                "Para regular la temperatura corporal"));

        //sensor setup
        sensorConfig();

        //setting initial screen (home fragment)
        setFragment();

        //ist onclick button
        showQuestions();

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


}
