package com.marco.example.utente.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Activity_Three extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__three);

        final RadioButton RB100V = (RadioButton) findViewById(R.id.RB100V);
        final RadioButton RB100F = (RadioButton) findViewById(R.id.RB100F);
        final RadioButton RB101V = (RadioButton) findViewById(R.id.RB101V);
        final RadioButton RB101F = (RadioButton) findViewById(R.id.RB101F);
        final RadioButton RB102V = (RadioButton) findViewById(R.id.RB102V);
        final RadioButton RB102F = (RadioButton) findViewById(R.id.RB102F);
        final RadioButton RB103V = (RadioButton) findViewById(R.id.RB103V);
        final RadioButton RB103F = (RadioButton) findViewById(R.id.RB103F);

        Button fine = (Button)findViewById(R.id.End3);
        fine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (RB100V.isChecked() && RB101V.isChecked() && RB102V.isChecked() && RB103V.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("corretto", "2");
                    Activity_Three.this.setResult(RESULT_OK, i);
                    Activity_Three.this.finish();

                } else{
                    Intent i = new Intent();
                    i.putExtra("corretto","3");
                    Activity_Three.this.setResult(RESULT_OK, i);
                    Activity_Three.this.finish();
                }

            }
        });
    }
}
