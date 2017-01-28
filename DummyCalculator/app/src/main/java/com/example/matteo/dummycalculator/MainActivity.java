package com.example.matteo.dummycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {


    protected TextView operation;
    protected TextView result;

    private Button[] numbers;
    private Button addizione;
    private Button sottrazione;
    private Button moltiplicazione;
    private Button divisione;
    private Button uguale;
    private Button punto;

    private String strOp;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        numbers[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "0";
                operation.setText(strOp);

            }
        });

        numbers[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "1";
                operation.setText(strOp);
            }
        });

        numbers[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "2";
                operation.setText(strOp);
            }
        });

        numbers[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "3";
                operation.setText(strOp);
            }
        });

        numbers[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "4";
                operation.setText(strOp);
            }
        });

        numbers[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "5";
                operation.setText(strOp);
            }
        });
        numbers[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "6";
                operation.setText(strOp);
            }
        });
        numbers[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "7";
                operation.setText(strOp);
            }
        });
        numbers[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "8";
                operation.setText(strOp);
            }
        });
        numbers[9].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "9";
                operation.setText(strOp);
            }
        });

        addizione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "+";
                operation.setText(strOp);
            }
        });

        punto.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                strOp = strOp + ".";
                operation.setText(strOp);
            }
        });

        sottrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "-";
                operation.setText(strOp);
            }
        });

        moltiplicazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "*";
                operation.setText(strOp);
            }
        });

        divisione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "/";
                operation.setText(strOp);
            }
        });

        uguale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                strOp = strOp + "=";

            }
        });



    }


    private void getViews() {
        strOp = "";
        operation = (TextView) findViewById(R.id.operazione);
        result = (TextView) findViewById(R.id.risultato);

        numbers = new Button[10];

        numbers[0] = (Button) findViewById(R.id.zeri);
        numbers[1] = (Button) findViewById(R.id.uno);
        numbers[2] = (Button) findViewById(R.id.due);
        numbers[3] = (Button) findViewById(R.id.tre);
        numbers[4] = (Button) findViewById(R.id.quattro);
        numbers[5] = (Button) findViewById(R.id.cinque);
        numbers[6] = (Button) findViewById(R.id.sei);
        numbers[7] = (Button) findViewById(R.id.sette);
        numbers[8] = (Button) findViewById(R.id.otto);
        numbers[9] = (Button) findViewById(R.id.nove);

        divisione = (Button) findViewById(R.id.divisione);
        addizione = (Button) findViewById(R.id.addizione);
        sottrazione = (Button) findViewById(R.id.sottrazione);
        moltiplicazione = (Button) findViewById(R.id.moltiplicazione);
        uguale = (Button) findViewById(R.id.uguale);
        punto = (Button) findViewById(R.id.punto);
    }
}
