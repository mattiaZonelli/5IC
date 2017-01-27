package com.marco.example.utente.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Activity_Two extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__two);

        final RadioButton RB1V = (RadioButton) findViewById(R.id.RB1V);
        final RadioButton RB1F = (RadioButton) findViewById(R.id.RB1F);
        final RadioButton RB2V = (RadioButton) findViewById(R.id.RB2V);
        final RadioButton RB2F = (RadioButton) findViewById(R.id.RB2F);
        final RadioButton RB3V = (RadioButton) findViewById(R.id.RB3V);
        final RadioButton RB3F = (RadioButton) findViewById(R.id.RB3F);
        final RadioButton RB4V = (RadioButton) findViewById(R.id.RB4V);
        final RadioButton RB4F = (RadioButton) findViewById(R.id.RB4F);

        Button fine = (Button)findViewById(R.id.End2);
        fine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (RB1V.isChecked() && RB2F.isChecked() && RB3V.isChecked() && RB4V.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("corretto", "2");
                    Activity_Two.this.setResult(RESULT_OK, i);
                    Activity_Two.this.finish();
                } else{
                    Intent i = new Intent();
                    i.putExtra("corretto","3");
                    Activity_Two.this.setResult(RESULT_OK, i);
                    Activity_Two.this.finish();
                }

            }
        });





    }
}
