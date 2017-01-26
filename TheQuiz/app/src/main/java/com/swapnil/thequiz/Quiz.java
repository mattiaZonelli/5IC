package com.swapnil.thequiz;

/**
 * Created by Swapnil on 24/01/2017.
 */

public class Quiz {
    private String question;
    private String answer;

    public Quiz(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
