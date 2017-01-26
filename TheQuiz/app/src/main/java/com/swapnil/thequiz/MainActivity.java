package com.swapnil.thequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Swapnil Paul.
 */
public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edt = (EditText) findViewById(R.id.editTxt);


        ((Button) findViewById(R.id.button01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                Bundle b = new Bundle();
                b.putString("user", edt.getText().toString());
                b.putInt("val",1);
                intent.putExtras(b);
                startActivity(intent);


            }
        });

        ((Button) findViewById(R.id.button02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                Bundle b = new Bundle();
                b.putString("user", edt.getText().toString());
                b.putInt("val",2);
                intent.putExtras(b);
                startActivity(intent);

            }
        });

    }
}
