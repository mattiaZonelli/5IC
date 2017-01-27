package com.quiz;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class Summary extends Activity {

    private final int QUESTION = 1;
    private final int ANSWER_1 = 2;
    private final int ANSWER_2 = 3;
    private final int ANSWER_3 = 4;
    private final int ANSWER_4 = 5;
    private final int CORRECT_ANSWER = 6;
    private final int CODE = 3;
    private int score = 0;
    private String[][] questions;
    private String[] givenAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        questions = (String[][]) getIntent().getBundleExtra("QUESTIONS").getSerializable("QUESTIONS");
        givenAnswers = getIntent().getStringArrayExtra("ANSWERS");

        Button endButton = (Button) findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this, Main_Activity.class);
                intent.putExtra("SCORE", score);
                setResult(CODE, intent);
                Summary.this.finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Main_Activity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (int i = 0; i < givenAnswers.length; i++) {
            if (questions[i][Integer.parseInt(questions[i][CORRECT_ANSWER])].equals(givenAnswers[i])) {
                score++;
            }
        }
        // System.out.println("GIVEN ANSWERS: "+Arrays.toString(givenAnswers));


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < questions.length; i++) {
            System.out.println(Arrays.toString(questions[i]));
            SummaryQuestion question = SummaryQuestion.newInstance(questions[i][QUESTION], questions[i][ANSWER_1], questions[i][ANSWER_2], questions[i][ANSWER_3], questions[i][ANSWER_4], questions[i][Integer.parseInt(questions[i][CORRECT_ANSWER])], givenAnswers[i]);
            fragmentTransaction.add(R.id.background, question, "QUESTION");
        }
        fragmentTransaction.commit();
    }

}
