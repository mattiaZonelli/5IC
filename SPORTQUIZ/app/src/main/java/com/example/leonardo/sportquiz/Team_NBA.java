package com.example.leonardo.sportquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Team_NBA extends AppCompatActivity {



        int giuste = 0;
        int sbagliate = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_team__nb);

            RadioButton RBf1 = (RadioButton) findViewById(R.id.rfalse1);

            RadioButton RBt1 = (RadioButton) findViewById(R.id.rtrue1);
            RadioButton RBf2 = (RadioButton) findViewById(R.id.rfalse2);
            RadioButton RBt2 = (RadioButton) findViewById(R.id.rtrue2);
            RadioButton RBf3 = (RadioButton) findViewById(R.id.rfalse3);
            RadioButton RBt3 = (RadioButton) findViewById(R.id.rtrue3);
            RadioButton RBf4 = (RadioButton) findViewById(R.id.rfalse4);
            RadioButton RBt4 = (RadioButton) findViewById(R.id.rtrue4);

            Button buttonExit = (Button) findViewById(R.id.buttonExit1);

        }


        public void onRadioButtonClicked(View view) {

            boolean check = ((RadioButton) view).isChecked();
            switch (view.getId()) {
                case R.id.rfalse1:
                    if (check)
                        sbagliate++;

                case R.id.rtrue1:
                    if (check)
                        giuste++;

            }
            switch (view.getId()) {
                case R.id.rfalse2:
                    if (check)
                        sbagliate++;

                case R.id.rtrue2:
                    if (check)
                        giuste++;
            }
            switch (view.getId()) {
                case R.id.rfalse3:
                    if (check)
                        sbagliate++;

                case R.id.rtrue3:
                    if (check)
                        giuste++;

            }
            switch (view.getId()) {
                case R.id.rfalse4:
                    if (check)
                        sbagliate++;

                case R.id.rtrue4:
                    if (check)
                        giuste++;
            }

        }

        protected void checkAnswer(View view) {
            //Bundle bundle = new Bundle();
            if (giuste == 4) {
                Intent C = new Intent();
                C.setAction("com.example.leonardo.sportquiz.MainActivity");
                setResult(0, C);
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                //bundle.putString("risultato", "vittoria");
                //intent.putExtras(bundle);
                //startActivity(intent);
                Toast.makeText(getBaseContext(), "Hai completato correttamente il quiz!", Toast.LENGTH_SHORT).show();
                finish();

            } else {

                Intent C = new Intent();
                C.setAction("com.example.leonardo.sportquiz.MainActivity");
                setResult(0, C);

                Toast.makeText(getBaseContext(), "Non hai completato correttamente il quiz!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }

    }


