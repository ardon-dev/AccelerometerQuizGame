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

    @Query("SELECT question_question FROM question WHERE question_id = :id")
    String getSingleQuestion(int id);

    @Query("SELECT question_answer_1 FROM question WHERE question_id = :id")
    String getSingleAnswerOne(int id);

    @Query("SELECT question_answer_2 FROM question WHERE question_id = :id")
    String getSingleAnswerTwo(int id);

    @Query("SELECT question_answer_right FROM question WHERE question_id = :id")
    String getSingleAnswerRight(int id);

}
