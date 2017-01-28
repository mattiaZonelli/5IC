package com.gallez.pool.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by pool on 26/01/17.
 */

public class Question implements Serializable{
    String question;
    String [] answers;
    String correct;
    public static final int MAX_QUESTION_PER_QUIZ = 10;

    public Question(String [] data){
        question = data[0];
        correct = data[1];
        answers = Arrays.copyOfRange(data,1,5);
        Collections.shuffle(Arrays.asList(answers));
    }

    public Question(){
        answers = new String[4];
    }

    public void addAnswer(String answer,int index){
        answers[index] = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", correct='" + correct + '\'' +
                '}';
    }
}
