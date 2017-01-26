package com.example.eleonora.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


/**
 * Created by Eleonora on 21/01/2017.
 */

public class Activity_One extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        final RadioButton rB1_1 = (RadioButton) findViewById(R.id.radiobutton1_1);
        final RadioButton rB1_2 = (RadioButton) findViewById(R.id.radiobutton1_2);
        final RadioButton rB1_3 = (RadioButton) findViewById(R.id.radiobutton1_3);
        final RadioButton rB1_4 = (RadioButton) findViewById(R.id.radiobutton1_4);
        final RadioButton rB1_5 = (RadioButton) findViewById(R.id.radiobutton1_5);
        final RadioButton rB1_6 = (RadioButton) findViewById(R.id.radiobutton1_6);
        final RadioButton rB1_7 = (RadioButton) findViewById(R.id.radiobutton1_7);
        final RadioButton rB1_8 = (RadioButton) findViewById(R.id.radiobutton1_8);


        Button end = (Button) findViewById(R.id.Fine1);
        end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(rB1_2.isChecked()&& rB1_3.isChecked()&& rB1_5.isChecked()&& rB1_8.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("key","1");
                    Activity_One.this.setResult(RESULT_OK, i);
                    Activity_One.this.finish();
                } else{
                    Intent i = new Intent();
                    i.putExtra("key","0");
                    Activity_One.this.setResult(RESULT_OK, i);
                    Activity_One.this.finish();
                }

            }
        });


    }


}

