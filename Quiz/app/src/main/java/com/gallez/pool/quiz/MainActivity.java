package com.gallez.pool.quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    static ArrayList<Question> shuffledQuestions;
    static Object[] choosenQuestions;
    public static final int ACTIVITY_TWO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager am = getAssets();
        databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Question> questions = null;
        try{
            questions = QuestionFileGrabber.convertFileToQuestion(am.open("questions.csv"));

        }catch(IOException e){
            e.printStackTrace();
        }
        if(DatabaseHelper.isTableEmpty(db))
            DatabaseHelper.populateDatabase(db,questions);
        shuffledQuestions = DatabaseHelper.getQuestionsAndAnswers(db);
        findViewById(R.id.startQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(shuffledQuestions);
                choosenQuestions = shuffledQuestions.subList(0,Question.MAX_QUESTION_PER_QUIZ+1).toArray();
                Intent i = new Intent(MainActivity.this,QuestionsActivity.class);
                i.putExtra("questions",choosenQuestions);
                startActivityForResult(i, ACTIVITY_TWO);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {//Toast.makeText(getBaseContext(),Arrays.deepToString((Object[])data.getExtras().get("result")),Toast.LENGTH_LONG);
            int [] array = (int[]) data.getExtras().get("result");
            int countCorrect = 0;
            for(int i = 0; i < Question.MAX_QUESTION_PER_QUIZ; i++){
                Question current = (Question)choosenQuestions[i];
                if(current.getAnswers()[array[i]].equals(current.getCorrect()))
                    countCorrect+=1;
            }
            Toast.makeText(getBaseContext(),(countCorrect>Question.MAX_QUESTION_PER_QUIZ/2)?"Hai Passato correttamente il Quiz!":"Non hai passato il Quiz!",Toast.LENGTH_LONG).show();
        }
    }
}
