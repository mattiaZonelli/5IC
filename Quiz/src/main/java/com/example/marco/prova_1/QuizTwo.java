package com.example.marco.prova_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.marco.prova_1.R.id.questionCount;

/**
 * Created by Marco on 22/01/2017.
 */

public class QuizTwo extends AppCompatActivity {

    Button buttonTrue, buttonFalse;
    TextView quizQuestionView, quizTypeView, questionCount;
    ProgressBar progressBar;
    MyCountDownTimer myCountDownTimer;

    DBHelper DB;
    SQLiteDatabase SQL;
    Cursor CURSOR;

    int STEP = 0;
    int MAX_QUESTION = 3;
    List <questionAnswer> QUIZ;
    ArrayList<Integer> randomNumbers;

    //IMPORTANT VARIABLE GAME
    int answerRight = 0, answerWrong = 0, completedAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_two);

        buttonTrue = (Button) findViewById(R.id.buttonTrue2);
        buttonFalse = (Button) findViewById(R.id.buttonFalse2);

        quizQuestionView = (TextView) findViewById(R.id.quizQuestionView2);
        quizTypeView = (TextView) findViewById(R.id.quizTypeView2);
        questionCount = (TextView) findViewById(R.id.questionCount2);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false); // May not be necessary
        progressBar.setMax(9);

        openDatabase();
        loadDataFromDatabase();
        System.out.println("Size" +QUIZ.size());
        getRandomQuestions(MAX_QUESTION, QUIZ.size());

        questionCount.setText("Domanda " + (STEP+1) + " di " + MAX_QUESTION);
        quizQuestionView.setText(searchAttribute(randomNumbers.get(STEP)).getQuestion());
        quizTypeView.setText(searchAttribute(randomNumbers.get(STEP)).getType());
        myCountDownTimer = new MyCountDownTimer(10000, 1000);
        myCountDownTimer.start();

        closeDatabase();
    }

    public void openDatabase(){
        try {
            DB = new DBHelper(this);
        } catch (IOException e) {}

        DB.opendatabase();
    }

    public void closeDatabase(){
        DB.close();
    }

    public void loadDataFromDatabase(){
        SQL = DB.getReadableDatabase();
        QUIZ = new ArrayList<>();
        CURSOR = SQL.query(DB.TABLE_NAME, DB.allColumns, null, null, null, null, null);

        CURSOR.moveToFirst();
        while(!CURSOR.isAfterLast()){
            questionAnswer qA = cursorToClass();
            QUIZ.add(qA);
            CURSOR.moveToNext();
        }
    }

    public questionAnswer cursorToClass(){
        questionAnswer qA = new questionAnswer();
        qA.setID(CURSOR.getInt(0));
        qA.setType(CURSOR.getString(1));
        qA.setQuestion(CURSOR.getString(2));
        qA.setAnswerTrue(CURSOR.getInt(3));
        qA.setAnswerFalse(CURSOR.getInt(4));

        return qA;
    }

    public void getRandomQuestions(int quantity, int count){
        int index = 0;
        randomNumbers = new ArrayList();
        while(index < quantity){
            int randomNumber = (int)(Math.random() * count) + 1;
            System.out.println("RANDOM: " +randomNumber);
            if(!randomNumbers.contains(randomNumber)){
                randomNumbers.add(randomNumber);
                index++;
            }
        }
    }

    public questionAnswer searchAttribute(int ID){
        questionAnswer qA = new questionAnswer();
        for(questionAnswer temp : QUIZ){
            if(temp.getID() == ID){
                qA = temp;
                break;
            }
        }
        return qA;
    }

    public void buttonOnClick(View view){
        switch(view.getId()){
            case R.id.buttonTrue2:
                checkAnswer(true,false,1);
                System.out.println("RIGHT: " + answerRight);
                System.out.println("WRONG: " + answerWrong);
                System.out.println("COMPLETED: " + completedAnswer);
                break;
            case R.id.buttonFalse2:
                checkAnswer(false,true,2);
                System.out.println("RIGHT: " + answerRight);
                System.out.println("WRONG: " + answerWrong);
                System.out.println("COMPLETED: " + completedAnswer);
                break;
        }
    }

    public void colorButton(){
        if(searchAttribute(randomNumbers.get(STEP)).getClicked() == 1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonTrue.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.buttonBackgroundTrue));
                buttonFalse.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.grey));
            }
        }else if (searchAttribute(randomNumbers.get(STEP)).getClicked() == 2){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                buttonFalse.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.buttonBackgroundFalse));
                buttonTrue.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.grey));
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonTrue.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.grey));
                buttonFalse.setBackgroundTintList(ContextCompat.getColorStateList(QuizTwo.this, R.color.grey));
            }
        }
    }

    public void checkAnswer(boolean answerTrue, boolean answerFalse, int type){
        if(answerTrue) {
            if (searchAttribute(randomNumbers.get(STEP)).getAnswerTrue() == 1) {
                if (searchAttribute(randomNumbers.get(STEP)).getClicked() == 0) {
                    searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                    colorButton();
                    answerRight++;
                    completedAnswer++;
                } else {
                    if(searchAttribute(randomNumbers.get(STEP)).getClicked() == 2){
                        searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                        colorButton();
                        answerRight++;
                        if (answerWrong != 0) {
                            answerWrong--;
                        }
                    }
                }
            } else {
                if (searchAttribute(randomNumbers.get(STEP)).getClicked() == 0) {
                    searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                    colorButton();
                    answerWrong++;
                    completedAnswer++;
                } else {
                    if(searchAttribute(randomNumbers.get(STEP)).getClicked() == 2){
                        searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                        colorButton();
                        answerWrong++;
                        if (answerRight != 0) {
                            answerRight--;
                        }
                    }
                }
            }
        }else{
            if (searchAttribute(randomNumbers.get(STEP)).getAnswerFalse() == 1) {
                if (searchAttribute(randomNumbers.get(STEP)).getClicked() == 0) {
                    searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                    colorButton();
                    answerRight++;
                    completedAnswer++;
                } else {
                    if(searchAttribute(randomNumbers.get(STEP)).getClicked() == 1){
                        searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                        colorButton();
                        answerRight++;
                        if (answerWrong != 0) {
                            answerWrong--;
                        }
                    }
                }
            } else {
                if (searchAttribute(randomNumbers.get(STEP)).getClicked() == 0) {
                    searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                    colorButton();
                    answerWrong++;
                    completedAnswer++;
                } else {
                    if(searchAttribute(randomNumbers.get(STEP)).getClicked() == 1){
                        searchAttribute(randomNumbers.get(STEP)).setClicked(type);
                        colorButton();
                        answerWrong++;
                        if (answerRight != 0) {
                            answerRight--;
                        }
                    }
                }
            }
        }
    }


    public void changeQuestion(){
        if(STEP < MAX_QUESTION-1){
            STEP++;
            colorButton();
            questionCount.setText("Domanda " + (STEP+1) + " di " + MAX_QUESTION);
            quizQuestionView.setText(searchAttribute(randomNumbers.get(STEP)).getQuestion());
            quizTypeView.setText(searchAttribute(randomNumbers.get(STEP)).getType());
            myCountDownTimer = new MyCountDownTimer(10000, 1000);
            myCountDownTimer.start();
        }else{
            alert();
        }
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Quiz terminato !");
        builder.setMessage("Il quiz è terminato e a breve verrà mostrato il risultato ottenuto.");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeQuiz();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void closeQuiz(){
        Intent i = new Intent();
        if(answerRight == MAX_QUESTION){
            i.putExtra("check", "completato correttamente." + answerRight + "," +
                        answerWrong + "," + completedAnswer);
        }else{
            i.putExtra("check", "non completato correttamente." + answerRight + "," +
                        answerWrong + "," + completedAnswer);
        }

        QuizTwo.this.setResult(RESULT_OK, i);
        QuizTwo.this.finish();
    }

    public class MyCountDownTimer extends CountDownTimer {

        int progress;

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            int progress = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            progress += 1;

            progressBar.setProgress(progressBar.getMax() - progress);
        }

        @Override
        public void onFinish() {
            changeQuestion();
        }
    }
}
