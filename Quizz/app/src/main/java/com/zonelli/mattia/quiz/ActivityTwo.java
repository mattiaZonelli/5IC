package com.zonelli.mattia.quiz;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
public class ActivityTwo extends AppCompatActivity {
    RadioGroup rg1,rg2,rg3,rg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Toast.makeText(getBaseContext(), "Sneakers Quiz is Started!",
                Toast.LENGTH_SHORT).show();

        rg1=(RadioGroup)findViewById(R.id.rgroup1);
        rg2=(RadioGroup)findViewById(R.id.rgroup2);
        rg3=(RadioGroup)findViewById(R.id.rgroup3);
        rg4=(RadioGroup)findViewById(R.id.rgroup4);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                if(rg1.getCheckedRadioButtonId()==R.id.radio1 && rg2.getCheckedRadioButtonId()==R.id.radio3 &&
                        rg3.getCheckedRadioButtonId()==R.id.radio5 && rg4.getCheckedRadioButtonId()==R.id.radio7){

                    Intent i = new Intent();
                    i.putExtra("key", "c");
                    ActivityTwo.this.setResult(RESULT_OK, i);
                    ActivityTwo.this.finish();

                } else {
                    Intent i = new Intent();
                    i.putExtra("key", "f");
                    ActivityTwo.this.setResult(RESULT_OK, i);
                    ActivityTwo.this.finish();
                }


            }
        });


    }

}
