package com.example.marco.prova_1;

/**
 * Created by Marco on 26/01/2017.
 */

public class questionAnswer {

    private int KEY_ID;
    private int KEY_ANSWER_TRUE;
    private int KEY_ANSWER_FALSE;
    private String KEY_TYPE;
    private String KEY_QUESTION;
    private int clicked = 0;

    public questionAnswer(){}

    //GETTER
    public int getID(){
        return KEY_ID;
    }

    public int getAnswerTrue(){
        return KEY_ANSWER_TRUE;
    }

    public int getAnswerFalse(){
        return KEY_ANSWER_FALSE;
    }

    public String getType(){
        return KEY_TYPE;
    }

    public String getQuestion(){
        return KEY_QUESTION;
    }

    public int getClicked(){
        return clicked;
    }


    //SETTER
    public void setID(int ID){
        KEY_ID = ID;
    }

    public void setAnswerTrue(int answerTrue){
        KEY_ANSWER_TRUE = answerTrue;
    }

    public void setAnswerFalse(int answerFalse){
        KEY_ANSWER_FALSE = answerFalse;
    }

    public void setType(String type){
        KEY_TYPE = type;
    }

    public void setQuestion(String question){
        KEY_QUESTION = question;
    }

    public void setClicked(int clicked){
        this.clicked = clicked;
    }


    //OBJECT STRING
    public String toString(){
        return "ID: " + KEY_ID + ", TYPE: " + KEY_TYPE + ", QUESTION: " + KEY_QUESTION +
                ", ANSWER TRUE: " + KEY_ANSWER_TRUE + ", ANSWER FALSE: " + KEY_ANSWER_FALSE;
    }

    public boolean isEqualClickedAnswer(int type){
        return clicked == type;
    }

}
