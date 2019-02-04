package com.example.vege.quizgame.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insertAllQuestion(Question... questions);

    @Query("SELECT * FROM question")
    List<Question> getAllQuestion();

}
