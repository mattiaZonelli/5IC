package com.example.leonardo.sportquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TEAM_NBA=2;
    static final int REQUEST_PLAYER_NBA=3;
    //TextView tv = (TextView)findViewById(R.id.textViewinit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.bottonequiz);
        Button b2 = (Button) findViewById(R.id.bottoneplayer);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q = new Intent(MainActivity.this,Team_NBA.class);
                startActivityForResult(q, REQUEST_TEAM_NBA);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View m) {
                Intent q = new Intent(MainActivity.this,Player_NBA.class);
                startActivityForResult(q,REQUEST_PLAYER_NBA);
            }
        });

        //Intent intent = getIntent();

        //Bundle bundle = intent.getExtras();
        //if (bundle.isEmpty()){
        //    tv.setText("Choose a quiz of NBA!");
        //}else{
        //    tv.setText(bundle.getString("risultato"));
        //}
        //bundle.clear();

        //tv.setText("Choose another quiz");
}



}