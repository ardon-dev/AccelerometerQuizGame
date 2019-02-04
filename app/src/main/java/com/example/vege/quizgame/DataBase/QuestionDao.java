package com.example.vege.quizgame.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface QuestionDao {

    @Insert
    void insertAllQuestion(Question... questions);

}
