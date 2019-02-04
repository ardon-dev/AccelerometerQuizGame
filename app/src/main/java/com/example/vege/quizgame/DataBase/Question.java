package com.example.vege.quizgame.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    public int question_id;

    @ColumnInfo(name = "question_question")
    public String question_question;

    @ColumnInfo(name = "question_answer_1")
    public String question_answer_1;

    @ColumnInfo(name = "question_answer_2")
    public String question_answer_2;

    public Question(String question_question, String question_answer_1, String question_answer_2) {
        this.question_question = question_question;
        this.question_answer_1 = question_answer_1;
        this.question_answer_2 = question_answer_2;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_question() {
        return question_question;
    }

    public void setQuestion_question(String question_question) {
        this.question_question = question_question;
    }

    public String getQuestion_answer_1() {
        return question_answer_1;
    }

    public void setQuestion_answer_1(String question_answer_1) {
        this.question_answer_1 = question_answer_1;
    }

    public String getQuestion_answer_2() {
        return question_answer_2;
    }

    public void setQuestion_answer_2(String question_answer_2) {
        this.question_answer_2 = question_answer_2;
    }
}
