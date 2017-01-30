package com.marco.example.utente.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ArrayList<String> arrayList = new ArrayList<>();
    String string = "";
    String string1 = "";

    public void onClick1(View view) {

        TextView display = (TextView) findViewById(R.id.display);
        Button button = (Button) view;
        string = (String) button.getText().toString();

        if (!string.contains("+") && !string.contains("-") && !string.contains("*") && !string.contains("/")) {
            string1 = string1+ string;

            if (arrayList.size()> 0) {

                arrayList.remove((arrayList.size() - 1));
            }
            arrayList.add(string1);
        } else {
            arrayList.add(string);
            arrayList.add(string);
            string1 = "";


        }
        display.setText(arrayList.toString());
    }

    public void onClick(View view) {
        TextView display1 = (TextView) findViewById(R.id.display1);
        int calc = 0;
        int c = arrayList.size();

        while (c != 1) {

            if (c > 3) {
                if (arrayList.get(3).contains("*") || arrayList.get(3).contains("/")) {
                    if (arrayList.get(3).contains("*")) {
                        calc = Integer.parseInt(arrayList.get(2)) * Integer.parseInt(arrayList.get(4));
                    }
                    if (arrayList.get(3).contains("/")) {
                        calc = Integer.parseInt(arrayList.get(2)) / Integer.parseInt(arrayList.get(4));
                    }
                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.remove(2);
                    arrayList.add(2, Integer.toString(calc));
                    c = arrayList.size();
                } else {
                    if (arrayList.get(1).contains("+")) {
                        calc = Integer.parseInt(arrayList.get(0)) + Integer.parseInt(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("-")) {
                        calc = Integer.parseInt(arrayList.get(0)) - Integer.parseInt(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("*")) {
                        calc = Integer.parseInt(arrayList.get(0)) * Integer.parseInt(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("/")) {
                        calc = Integer.parseInt(arrayList.get(0)) / Integer.parseInt(arrayList.get(2));
                    }
                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.remove(0);
                    arrayList.add(0, Integer.toString(calc));
                    c = arrayList.size();
                }
            }
            else {
                if (arrayList.get(1).contains("+")) {
                    calc = Integer.parseInt(arrayList.get(0)) + Integer.parseInt(arrayList.get(2));
                }
                if (arrayList.get(1).contains("-")) {
                    calc = Integer.parseInt(arrayList.get(0)) - Integer.parseInt(arrayList.get(2));
                }
                if (arrayList.get(1).contains("*")) {
                    calc = Integer.parseInt(arrayList.get(0)) * Integer.parseInt(arrayList.get(2));
                }
                if (arrayList.get(1).contains("/")) {
                    calc = Integer.parseInt(arrayList.get(0)) / Integer.parseInt(arrayList.get(2));
                }
                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.remove(0);
                arrayList.add(0, Integer.toString(calc));
                c = arrayList.size();
            }

        }

        display1.setText(Integer.toString(calc));

    }

    public void clear(View view){
        TextView display = (TextView)findViewById(R.id.display);
        TextView display1 = (TextView)findViewById(R.id.display1);

        string="";
        string1="";
        display.setText("0");
        display1.setText("");

    }
}
