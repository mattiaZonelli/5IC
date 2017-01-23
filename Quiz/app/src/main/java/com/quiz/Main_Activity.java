package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newActivity=(Button)(findViewById(R.id.newActivityButton));
        Button quit=(Button)(findViewById(R.id.quitButton));

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Activity.this,QuestionActivity.class);
                startActivity(intent);


                try {

                      BufferedReader br=new BufferedReader(new InputStreamReader(getResources().getAssets().open("./database/QuizDataBase.sqlite")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SQLiteDatabase sqLiteDatabase= SQLiteDatabase.openDatabase(getClass().getResource("./raw/database/QuizDatabase.sqlite").getFile(),null,SQLiteDatabase.OPEN_READWRITE);
            }
        });
    }
}
