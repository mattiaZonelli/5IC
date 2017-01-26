package com.zonelli.mattia.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActivityThree extends AppCompatActivity {
    RadioGroup rg1, rg2,rg3, rg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        Toast.makeText(getBaseContext(), "You are in the QUIZ!",
                Toast.LENGTH_SHORT).show();
        rg1=(RadioGroup)findViewById(R.id.rgroup1);
        rg2=(RadioGroup)findViewById(R.id.rgroup2);
        rg3=(RadioGroup)findViewById(R.id.rgroup3);
        rg4=(RadioGroup)findViewById(R.id.rgroup4);

        Button btn = (Button) findViewById(R.id.buttonF);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(rg1.getCheckedRadioButtonId()==R.id.radio1 && rg2.getCheckedRadioButtonId()==R.id.radio4 &&
                        rg3.getCheckedRadioButtonId()==R.id.radio5 && rg4.getCheckedRadioButtonId()==R.id.radio8){

                    Intent i = new Intent();
                    i.putExtra("key", "c");
                    ActivityThree.this.setResult(RESULT_OK, i);
                    ActivityThree.this.finish();


                } else {
                    Intent i = new Intent();
                    i.putExtra("key", "f");
                    ActivityThree.this.setResult(RESULT_OK, i);
                    ActivityThree.this.finish();
                }

            }
        });
    }
}
