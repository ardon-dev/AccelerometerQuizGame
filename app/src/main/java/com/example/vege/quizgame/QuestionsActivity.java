package com.example.vege.quizgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.example.vege.quizgame.Adapter.QuestionAdapter;
import com.example.vege.quizgame.DataBase.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);

        mRecyclerView = findViewById(R.id.questionRecyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        questions = MainActivity.db.questionDao().getAllQuestion();
        mRecyclerView.setAdapter(new QuestionAdapter(questions));
    }


}
