package com.example.vege.quizgame;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.vege.quizgame.Fragments.GameFragment;

public class GameActivity extends AppCompatActivity {

    public static MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        //setting counter to 0 when user plays again
        GameFragment.counter = 0;

        musicStart();

        setFragment();

    }

    @Override
    protected void onPause() {
        super.onPause();

        music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        music.start();
    }

    private void setFragment() {
        //activity will start with home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.gameContainer, new GameFragment()).commit();

    }

    private void musicStart() {
        music = MediaPlayer.create(this, R.raw.sans_theme);
        music.setLooping(true);
        music.start();

    }

}
