package com.example.eleonora.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE1 = 1;
    static final int REQUEST_CODE2= 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Quiz1 = (Button) findViewById(R.id.Quiz1);
        Quiz1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity_One.class);

                startActivityForResult(i, REQUEST_CODE1);
            }
        });

        Button Quiz2 = (Button) findViewById(R.id.Quiz2);
        Quiz2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity_Two.class);

                startActivityForResult(i, REQUEST_CODE2);
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

          super.onActivityResult(requestCode,resultCode,data);
            if(requestCode== REQUEST_CODE1){
                if(resultCode==RESULT_OK){
                    String result = data.getStringExtra("key");
                    if (result.equals("1")) {
                        TextView view = (TextView) findViewById(R.id.textViewVinto);
                        view.setText("Complimenti. Hai Vinto!");
                    }

                    if (result.equals("0")) {
                        TextView view = (TextView) findViewById(R.id.textViewVinto);
                        view.setText("Hai Perso. Riprova!");
                    }
                }
            }

            if(requestCode== REQUEST_CODE2){
                if(resultCode==RESULT_OK){
                    String result = data.getStringExtra("key");
                    if (result.equals("1")) {
                        TextView view = (TextView) findViewById(R.id.textViewVinto);
                        view.setText("Complimenti. Hai Vinto!");
                    }

                    if (result.equals("0")) {
                        TextView view = (TextView) findViewById(R.id.textViewVinto);
                        view.setText("Hai Perso. Riprova!");
                    }
                }
            }

        }}

