package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends Activity {

    private String[][] questions;
    private int questionIndex = 0;

    private String givenAnswers[];

    private String[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questions = (String[][]) getIntent().getExtras().get("key");
        givenAnswers = new String[questions.length];

        correctAnswers = new String[questions.length];

        refresh();

        questionIndex = 0;

        Button nextButton = (Button) findViewById(R.id.nextQuestion);

        Button finishButton = (Button) findViewById(R.id.finishButton);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionIndex < questions.length) {
                    checkAnswer();
                    refresh();
                } else {
                    startSummary();
                }
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNullValues();
                startSummary();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Main_Activity.class));
        finish();
    }

    private void deleteNullValues() {
        for (int i = 0; i < givenAnswers.length; i++) {
            if (givenAnswers[i] == null) {
                givenAnswers[i] = " ";
            }
        }

        for (int i = 0; i < correctAnswers.length; i++) {

            if (correctAnswers[i] == null) {
                correctAnswers[i] = " ";
            }
        }
    }

    private void checkAnswer() {
        RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.group);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioButtonGroup.findViewById(radioButtonID);
        givenAnswers[questionIndex++] = radioButton == null ? " " : String.valueOf(radioButton.getText());
    }

    private void startSummary() {
        Intent intent = new Intent(QuestionActivity.this, Summary.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTIONS", questions);
        intent.putExtra("QUESTIONS", bundle);
        intent.putExtra("ANSWERS", givenAnswers);
        intent.putExtra("CORRECT_ANSWERS", correctAnswers);
        startActivity(intent);
    }

    private void refresh() {
        if (questionIndex < questions.length) {
            if ((findViewById(R.id.group)) != null) {
                ((RadioGroup) findViewById(R.id.group)).clearCheck();
                setQuestions(questionIndex);
            }
        } else {
            startSummary();
        }

    }

    private void setQuestions(int row) {
        TextView textView = (TextView) findViewById(R.id.question);
        RadioButton button1 = (RadioButton) findViewById(R.id.answer1);
        RadioButton button2 = (RadioButton) findViewById(R.id.answer2);
        RadioButton button3 = (RadioButton) findViewById(R.id.answer3);
        RadioButton button4 = (RadioButton) findViewById(R.id.answer4);
        textView.setText(questions[row][1]);
        correctAnswers[questionIndex] = questions[row][2];
        String[] answers = shuffle(row);
        button1.setText(answers[0]);
        button2.setText(answers[1]);
        button3.setText(answers[2]);
        button4.setText(answers[3]);
    }

    private String[] shuffle(int row) {
        String[] answers = {questions[row][2], questions[row][3], questions[row][4], questions[row][5]};
        //Collections.shuffle(Arrays.asList(answers));
        return answers;
    }
}
