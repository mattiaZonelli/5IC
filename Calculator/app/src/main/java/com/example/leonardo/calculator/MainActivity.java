package com.example.leonardo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//import com.example.leonardo.calculator.parser.ExpressionParser;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ArrayList<String> arrayList = new ArrayList<String>();
    String string = "";
    String stringAppoggio = "";


    //funzione per l'utilizzo dei pulsanti (numeri e operazioni)
    public void onClick1 (View v){

        TextView textView2 =(TextView) findViewById(R.id.textView2);
        Button button = (Button) v;

        string = (String) button.getText().toString();
        if (!string.contains("+") && !string.contains("-") && !string.contains("*") && !string.contains("/")){
            stringAppoggio = stringAppoggio+string;

            if (arrayList.size()>0){
                arrayList.remove((arrayList.size()-1));
            }

            arrayList.add(stringAppoggio);

        }
        else{
            arrayList.add(string);
            arrayList.add(string);
            stringAppoggio="";

        }

        //visualizza l'espressione nella textView in due metodi diversi

        //textView2.setText(textView2.getText().toString()+ string);
        textView2.setText(arrayList.toString());


    }

    //funzione che restituisce il risultato dell'espressione
    public void result (View v){

        TextView textView1 = (TextView)findViewById(R.id.textView);

        int calc = 0;
        int c = arrayList.size();

        while (c!=1){

            if (c>3){
                if(arrayList.get(3).contains("*") || arrayList.get(3).contains("+") || arrayList.get(3).contains("-") || arrayList.get(3).contains("/")){
                    if (arrayList.get(3).contains("*")){calc = Integer.parseInt(arrayList.get(2))*Integer.parseInt(arrayList.get(4));}
                    if (arrayList.get(3).contains("/")){calc = Integer.parseInt(arrayList.get(2))/Integer.parseInt(arrayList.get(4));}

                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.add(2,Integer.toString(calc));
                    c = arrayList.size();

                }
                else{
                    if (arrayList.get(1).contains("+")){calc = Integer.parseInt(arrayList.get(0))+Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("-")){calc = Integer.parseInt(arrayList.get(0))-Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("*")){calc = Integer.parseInt(arrayList.get(0))*Integer.parseInt(arrayList.get(2));}
                    if (arrayList.get(1).contains("/")){calc = Integer.parseInt(arrayList.get(0))/Integer.parseInt(arrayList.get(2));}

                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.add(0,Integer.toString(calc));
                    c = arrayList.size();
                }

        }
        else{

                if (arrayList.get(1).contains("+")){calc = Integer.parseInt(arrayList.get(0))+Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("-")){calc = Integer.parseInt(arrayList.get(0))-Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("*")){calc = Integer.parseInt(arrayList.get(0))*Integer.parseInt(arrayList.get(2));}
                if (arrayList.get(1).contains("/")){calc = Integer.parseInt(arrayList.get(0))/Integer.parseInt(arrayList.get(2));}

                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.add(0,Integer.toString(calc));
                c = arrayList.size();
        }
        }

        //visualizza nella textView inferiore il risultato dell'espressione
        textView1.setText(Integer.toString(calc));

    }


    //Funzione collegata al bottone C ---> rimuove i dati all'interno dell'Arraylist e azzera le variabili di appoggio
    public void clear (View vie){

        TextView textView4 = (TextView)findViewById(R.id.textView);
        TextView textView5 = (TextView)findViewById(R.id.textView2);

        stringAppoggio= "";
        string = "";
        textView4.setText("0");
        textView5.setText("");
        arrayList.clear();
    }




    }

