package com.marco.example.utente.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_ACTIVITY_TWO = 2;
    static final int REQUEST_ACRIVITY_THREE= 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Quiz1 = (Button) findViewById(R.id.Quiz1);
        Quiz1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity_Two.class);
                startActivityForResult(i, REQUEST_ACTIVITY_TWO);
            }
        });

        Button Quiz2 = (Button) findViewById(R.id.Quiz2);
        Quiz2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity_Three.class);
                startActivityForResult(i, REQUEST_ACRIVITY_THREE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_ACTIVITY_TWO){
            if (resultCode==RESULT_OK){
                String result = data.getStringExtra("corretto");
                if(result.equals("2"))
                    Toast.makeText(this, "Hai risposto correttamente a tutte le domande del quiz 1! GRANDE", Toast.LENGTH_LONG).show();
                if (result.equals("3")){
                    Toast.makeText(this, "Non hai risposto correttamente, Riprova", Toast.LENGTH_LONG).show();
                }
            }


        }

        if (requestCode == REQUEST_ACRIVITY_THREE){
            if (resultCode==RESULT_OK){
                String result = data.getStringExtra("corretto");
                if(result.equals("2"))
                    Toast.makeText(this, "Hai risposto correttamente a tutte le domande del quiz 2! GRANDE", Toast.LENGTH_LONG).show();
                    if (result.equals("3")){
                        Toast.makeText(this, "Non hai risposto correttamente, riprova!", Toast.LENGTH_LONG).show();
                    }
            }

        }
    }
}
