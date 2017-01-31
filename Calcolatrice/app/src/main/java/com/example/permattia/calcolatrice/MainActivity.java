package com.example.permattia.calcolatrice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    String str ="";
    int countOp = 0;
    String op;
    float x;
    float y;
    float ris = 0;
    boolean checkDoubleOperator;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.Risultato);
        tv.setText("0");

    }

    public void onClick(View view){
        Button b = (Button)  view;
        str = str + b.getText().toString();

        tv.setText(str);
        checkDoubleOperator = false;
    }

    public void delete (View view){
        if (!str.equals("")) {
            str = (String) str.subSequence(0, str.length() - 1);
        }
        tv.setText(str);
    }

    public void cancelAll (View view ){
        str = "";
        tv.setText(str);
    }

    public void getOperator(View view){
        if (!checkDoubleOperator){
            Button b = (Button) view;
            x = Float.parseFloat(tv.getText().toString());
            op= b.getText().toString();
            str = str + b.getText().toString();
            tv.setText(str);
            checkDoubleOperator = true;
            //Toast.makeText(getBaseContext(), op, Toast.LENGTH_SHORT).show();
        }


    }

    public void evalutate(View view){
        String support = str.substring(str.indexOf(op)+1,str.length());
        y = Float.parseFloat(support);

        if (op.equals("-")){

            ris = x - y ;
        } else  if (op.equals("+")){

            ris = x + y ;
        } else  if (op.equals("/")){
            ris = x /y ;
        } else  if (op.equals("*")){

            ris = x * y ;
        }


        x = 0;
        y = 0;
        tv.setText(Float.toString(ris));
        ris = 0;
        str = "";

    }



}
