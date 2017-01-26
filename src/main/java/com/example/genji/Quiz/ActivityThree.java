package com.example.genji.am002_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityThree extends AppCompatActivity {
    TextView reTextView;
    TextView rsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        reTextView = (TextView) findViewById(R.id.re);
        rsTextView = (TextView) findViewById(R.id.rs);

        Bundle bundle = getIntent().getExtras();
        Toast.makeText(this, Integer.toString(bundle.getInt("a")), Toast.LENGTH_SHORT);
        Toast.makeText(this, Integer.toString(bundle.getInt("b")), Toast.LENGTH_SHORT);


        reTextView.setText("Risposte esatte:" + String.valueOf(bundle.getInt("a")));
        rsTextView.setText("Risposte sbagliate:" + String.valueOf(bundle.getInt("b")));

        Button primaSchermata = (Button) findViewById(R.id.primaSchermata);

        primaSchermata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(ActivityThree.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}