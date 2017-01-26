package com.example.matteo.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int ACTIVITY_TWO = 2;
    static final int ACTIVITY_THREE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnQuizFacile = (Button) findViewById(R.id.buttonQuiz1);
        Button btnQuizDifficile = (Button) findViewById(R.id.buttonQuiz2);
        TextView res = (TextView) findViewById(R.id.mainResult);


        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        btnQuizFacile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityThree.class);
                startActivity(i);
                Toast toast = Toast.makeText(context, "BTNFACILE", duration);
                toast.show();
            }
        });

        btnQuizDifficile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityTwo.class);
                //startActivity(i);
                Toast toast = Toast.makeText(context, "BTNDIFFICILE", duration);
                toast.show();
                startActivityForResult(i, ACTIVITY_TWO);
            }
        });


    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        TextView res = (TextView) findViewById(R.id.mainResult);
        // check if the request code is 1 (we came from 1 ... )

        if (requestCode == ACTIVITY_TWO) {
            //---if the result is OK---
            if (resultCode == RESULT_OK) {
                // data passed in using getStringExtra() via Intent data
                res.setText(data.getStringExtra("isOK"));
            }


            }


        if (requestCode == ACTIVITY_THREE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("isOK"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
