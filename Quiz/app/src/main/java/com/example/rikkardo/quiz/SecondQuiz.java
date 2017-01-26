package com.example.rikkardo.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SecondQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_quiz);

        final RadioButton rb_01_01 = (RadioButton) findViewById(R.id.c0101);
        final RadioButton rb_01_02 = (RadioButton) findViewById(R.id.c0102);
        final RadioButton rb_02_01 = (RadioButton) findViewById(R.id.c0201);
        final RadioButton rb_02_02 = (RadioButton) findViewById(R.id.c0202);
        final RadioButton rb_03_01 = (RadioButton) findViewById(R.id.c0301);
        final RadioButton rb_03_02 = (RadioButton) findViewById(R.id.c0302);
        final RadioButton rb_04_01 = (RadioButton) findViewById(R.id.c0401);
        final RadioButton rb_04_02 = (RadioButton) findViewById(R.id.c0402);
        final RadioButton rb_05_01 = (RadioButton) findViewById(R.id.c0501);
        final RadioButton rb_05_02 = (RadioButton) findViewById(R.id.c0502);

        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent();
                if(rb_01_01.isChecked() && rb_02_02.isChecked() && rb_03_02.isChecked()
                        && rb_04_01.isChecked() && rb_05_02.isChecked()){
                    i.putExtra("win", "win");
                } else{
                    i.putExtra("win", "lose");
                }
                SecondQuiz.this.setResult(RESULT_OK, i);
                SecondQuiz.this.finish();
            }
        });
    }
}
