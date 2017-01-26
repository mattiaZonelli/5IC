package com.swapnil.thequiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Swapnil on 24/01/2017.
 */

public class QuizActivity extends Activity {

    Cursor c = null;
    private int cout = 0;
    private int j= -1;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Quiz qz;
    String user="";
    int quizNum;
    ArrayList<Quiz> arr;
    int punteggio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        Intent i = getIntent();
        user = i.getStringExtra("user");
        quizNum = i.getIntExtra("val",1);

        Toast.makeText(getApplicationContext(),
                "Buon divertimento " + user + "...", Toast.LENGTH_SHORT).show();

        final TextView tx = (TextView) findViewById(R.id.txtViewQuestion);


        DatabaseHelper myDbHelper = new DatabaseHelper(QuizActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
       arr = new ArrayList<>();
        c = myDbHelper.query("QUIZ", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
                String question = c.getString(1);
                String answers = c.getString(2);
                arr.add(new Quiz(question,answers));
            } while (c.moveToNext());
        }
        tx.setText("TAKE THE QUIZ ");

        if(quizNum == 2){
            cout = (arr.size()/2)-1;
            j=cout-1;
        }else{
            cout=0;
            j=-1;
        }

        ((Button) findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qz = arr.get(cout);
                tx.setText(qz.getQuestion());

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                if(cout>0) {
                    if (radioButton.getText().equals(arr.get(j).getAnswer())) {
                      punteggio++;
                    }
                }
                cout++;
                ++j;
                if(((cout == arr.size())&&quizNum==2) || ((cout==(arr.size()/2)+1) && quizNum==1)){
                    DatabaseHelper dh = new DatabaseHelper(QuizActivity.this);
                    dh.updateDB(user,"" + punteggio + "/" + arr.size()/2);
                    Intent intent = new Intent(QuizActivity.this, RankActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
}
