package com.quiz;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

public class Summary extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String[][] questions = (String[][]) getIntent().getBundleExtra("QUESTIONS").getSerializable("QUESTIONS");
        String [] correctAnswers= getIntent().getStringArrayExtra("CORRECT_ANSWERS");
        String [] givenAnswers= getIntent().getStringArrayExtra("ANSWERS");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < questions.length; i++) {
            SummaryQuestion question=SummaryQuestion.newInstance(questions[i][1],questions[i][2],questions[i][3],questions[i][4],questions[i][5],correctAnswers[i],givenAnswers[i]);
            fragmentTransaction.add(R.id.background, question, "QUESTION");
        }
        fragmentTransaction.commit();
    }
}
