package com.example.vege.quizgame.DataBase;

import java.util.ArrayList;
import java.util.List;

public class AllQuestions {

    public static List<Question> insertQuestions(){

        List<Question> questions = new ArrayList<>();

        questions.add(new Question("¿Qué inventaron los hermanos Lumières?",
                "El teléfono",
                "El cine",
                "El cine"));

        questions.add(new Question("¿Cuántos días tiene un año bisiesto?",
                "366 días",
                "365 días",
                "366 días"));

        questions.add(new Question("Pablo Picasso era…",
                "Un pintor",
                "Un escritor",
                "Un pintor"));

        questions.add(new Question("¿Quién se dice que es el rey de la selva?",
                "El león",
                "El tigre",
                "El león"));

        questions.add(new Question("Mozart era un genio del mundo de…",
                "La música",
                "La literatura",
                "La música"));

        questions.add(new Question("¿Cómo se llama la escritura utilizada por personas ciegas?",
                "Morse",
                "Braille",
                "Braille"));

        questions.add(new Question("¿Quién descubrió América?",
                "Colón",
                "Aristóteles",
                "Colón"));

        questions.add(new Question("¿Dónde se encuentra la Estatua de La Libertad?",
                "Los Angeles",
                "Nueva York",
                "Nueva York"));

        questions.add(new Question("¿Cuál es la capital de China?",
                "Hong Kong",
                "Tokio",
                "Hong Kong"));

        questions.add(new Question("¿Por qué sudamos?",
                "Para correr más",
                "Para regular nuestra temperatura corporal",
                "Para regular nuestra temperatura corporal"));

        return questions;
    }
}
