package com.example.lucabassanello.quiz;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.color.black;
import static android.R.color.holo_green_dark;

/**
 * Created by lucabassanello on 25/01/17.
 */

public class ActivityTwo  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        TextView t1= (TextView) findViewById(R.id.textView4);
        final Button b2a= (Button) findViewById(R.id.btn2a);
        final Button b1a= (Button) findViewById(R.id.btn1a);
        final Button b3a= (Button) findViewById(R.id.btn3a);
        final Button b1b= (Button) findViewById(R.id.btn1b);
        final Button b2b= (Button) findViewById(R.id.btn2b);
        final Button b3b= (Button) findViewById(R.id.btn3b);
        b2a.setOnClickListener(new View.OnClickListener() {
                                 public void onClick(View view) {

                                     b2a.setBackgroundColor(Color.GREEN);
                                     Toast.makeText(getBaseContext(),R.string.giusta,Toast.LENGTH_SHORT).show();
                                 }
                             });
        b1a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                b1a.setBackgroundColor(Color.RED);
                Toast.makeText(getBaseContext(),R.string.sbagliata,Toast.LENGTH_SHORT).show();
            }
        });
        b3a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                b3a.setBackgroundColor(Color.RED);
                Toast.makeText(getBaseContext(),R.string.sbagliata,Toast.LENGTH_SHORT).show();
            }
        });
        b1b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                b1b.setBackgroundColor(Color.RED);
                Toast.makeText(getBaseContext(),R.string.sbagliata,Toast.LENGTH_SHORT).show();
            }
        });
        b2b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                b2b.setBackgroundColor(Color.RED);
                Toast.makeText(getBaseContext(),R.string.sbagliata,Toast.LENGTH_SHORT).show();
            }
        });
        b3b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                b3b.setBackgroundColor(Color.GREEN);
                Toast.makeText(getBaseContext(),R.string.giusta,Toast.LENGTH_SHORT).show();
            }
        });


    }

}
