package com.example.marco.prova_1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    static final int QUIZ_ONE = 1;
    static final int QUIZ_TWO = 2;

    Typeface font;
    Button buttonQuizOne, buttonQuizTwo;
    TextView resultWin, resultMessage;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStyleButton();
        resultWin = (TextView) findViewById(R.id.resultWin);
        resultMessage = (TextView) findViewById(R.id.resultMessage);
    }

    protected void setStyleButton(){
        font = Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf");

        buttonQuizOne = (Button) findViewById(R.id.buttonQuizOne);
        buttonQuizOne.setTypeface(font);

        buttonQuizTwo = (Button) findViewById(R.id.buttonQuizTwo);
        buttonQuizTwo.setTypeface(font);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonQuizOne.setLetterSpacing((float)0.2);
            buttonQuizTwo.setLetterSpacing((float)0.2);
        }

    }

    protected void buttonOnClick(View view){
        switch(view.getId()){
            case R.id.buttonQuizOne:
                runActivity(QuizOne.class, QUIZ_ONE);
                break;
            case R.id.buttonQuizTwo:
                runActivity(QuizTwo.class, QUIZ_TWO);
                break;
        }
    }

    protected void runActivity(Class c, int TYPE_QUIZ){
        intent = new Intent(MainActivity.this, c);
        startActivityForResult(intent, TYPE_QUIZ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ_ONE) {
            if (resultCode == RESULT_OK) {
                showQuizResult("1", data.getStringExtra("check"));
            }
        }
        if (requestCode == QUIZ_TWO) {
            if (resultCode == RESULT_OK) {
                showQuizResult("2", data.getStringExtra("check"));
            }
        }
    }

    public void showQuizResult(String numberQuiz, String message){
        String [] data = message.split("\\.");
        resultWin.setText("Quiz " + numberQuiz + " " + data[0]);
        String [] results = data[1].split(",");
        resultMessage.setText("\nRisposte corrette: " + results[0] +
                                "\nRisposte sbagliate: " + results[1] +
                                "\nRisposte completate: " + results[2]);
    }
}
