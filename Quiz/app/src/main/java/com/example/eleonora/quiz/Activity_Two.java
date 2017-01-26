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

   public class Activity_Two extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        final RadioButton rB2_1 = (RadioButton) findViewById(R.id.radiobutton2_1);
        final RadioButton rB2_2 = (RadioButton) findViewById(R.id.radiobutton2_2);
        final RadioButton rB2_3 = (RadioButton) findViewById(R.id.radiobutton2_3);
        final RadioButton rB2_4 = (RadioButton) findViewById(R.id.radiobutton2_4);
        final RadioButton rB2_5 = (RadioButton) findViewById(R.id.radiobutton2_5);
        final RadioButton rB2_6 = (RadioButton) findViewById(R.id.radiobutton2_6);
        final RadioButton rB2_7 = (RadioButton) findViewById(R.id.radiobutton2_7);
        final RadioButton rB2_8 = (RadioButton) findViewById(R.id.radiobutton2_8);


        Button end = (Button) findViewById(R.id.Fine2);
        end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(rB2_2.isChecked()&& rB2_4.isChecked()&& rB2_5.isChecked()&& rB2_8.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("key","1");
                    Activity_Two.this.setResult(RESULT_OK, i);
                    Activity_Two.this.finish();
                } else{
                    Intent i = new Intent();
                    i.putExtra("key","0");
                    Activity_Two.this.setResult(RESULT_OK, i);
                    Activity_Two.this.finish();
                }

            }
        });


    }


}

