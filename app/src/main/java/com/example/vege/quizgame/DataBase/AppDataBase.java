package com.example.vege.quizgame.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Question.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract QuestionDao questionDao();
}
