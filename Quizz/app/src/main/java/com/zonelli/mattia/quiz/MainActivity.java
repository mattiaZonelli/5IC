package com.zonelli.mattia.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final int ACTIVITY_TWO = 2;
    static final int ACTIVITY_THREE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn2 = (Button) findViewById(R.id.buttonb);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityTwo.class);
                startActivityForResult(i,ACTIVITY_TWO);
            }
        });

        Button btn1 = (Button) findViewById(R.id.buttona);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityThree.class);
                startActivityForResult(i,ACTIVITY_THREE);
            }
        });





    }

   public void onActivityResult(int requestCode, int resultCode,Intent data){
       super.onActivityResult(requestCode,resultCode,data);
       if(requestCode== ACTIVITY_TWO){
           if(resultCode== RESULT_OK){
               String ris = data.getStringExtra("key");
               if(ris.equals("c")){
                   TextView tv = (TextView) findViewById(R.id.tvRis2);
                   tv.setText("Hai completato correttamente il secondo QUIZ!");

               }
               if(ris.equals("f")){
                   TextView tv = (TextView) findViewById(R.id.tvRis2);
                   tv.setText("NON hai completato correttamente il secondo QUIZ!");

               }
           }
       }
       if(requestCode== ACTIVITY_THREE){
           if(resultCode== RESULT_OK){
               String ris = data.getStringExtra("key");
               if(ris.equals("c")){
                   TextView tv = (TextView) findViewById(R.id.tvRis1);
                   tv.setText("Hai completato correttamente il primo QUIZ!");

               }
               if(ris.equals("f")){
                   TextView tv = (TextView) findViewById(R.id.tvRis1);
                   tv.setText("NON hai completato correttamente il primo QUIZ!");

               }
           }
       }



    }
}
