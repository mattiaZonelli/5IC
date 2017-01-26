package com.example.rikkardo.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_01 = 1;
    private final int REQUEST_CODE_02 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_1 = (Button) findViewById(R.id.button1);
        btn_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, FirstQuiz.class);
                startActivityForResult(i, REQUEST_CODE_01);
            }
        });

        Button btn_2 = (Button) findViewById(R.id.button2);
        btn_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, SecondQuiz.class);
                startActivityForResult(i, REQUEST_CODE_02);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getStringExtra("win");
            String message = "";
            if (requestCode == REQUEST_CODE_01) {
                if (result.equals("win")) {
                    message = "You won quiz 1!";
                } else {
                    message = "You lost quiz 1...";
                }
            } else if(requestCode==REQUEST_CODE_02){
                if(result.equals("win")){
                    message = "You won quiz 2!";
                } else {
                    message = "You lost quiz 2...";
                }
            }
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

}
