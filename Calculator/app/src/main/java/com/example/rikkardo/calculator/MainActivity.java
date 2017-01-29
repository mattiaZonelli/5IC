package com.example.rikkardo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int step;
    /**
     * step == 0    Waiting for first number
     *              If receive numbers, goes to step 1
     *              if receive expressions, goes to step 2
     * step == 1    Receiving first number
     *              If receive expressions, goes to step 2
     *              if receive delete, goes to step 0
     * step == 2    Waiting for second number
     *              If receive numbers, goes to step 3
     *              if receive delete, goes to step 0
     * step == 3    Receiving second number
     *              If receive equals, calculate and goes to step 4
*                   if receive expression, calculate and goes to step 2
*                   if receive delete, goes to step 2
     * step == 4    If receive delete, goes to step 0
     *              if receive number, goes to step 1
     *              if receive expression, goes to step 2
     */
    private float first_number;
    private float second_number;

    private int expression_code;
    private boolean decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        step = 0;
        first_number = 0;
        second_number = 0;
        expression_code = -1;
        decimal = false;

        Button n1 = (Button) findViewById(R.id.n1);
        Button n2 = (Button) findViewById(R.id.n2);
        Button n3 = (Button) findViewById(R.id.n3);
        Button n4 = (Button) findViewById(R.id.n4);
        Button n5 = (Button) findViewById(R.id.n5);
        Button n6 = (Button) findViewById(R.id.n6);
        Button n7 = (Button) findViewById(R.id.n7);
        Button n8 = (Button) findViewById(R.id.n8);
        Button n9 = (Button) findViewById(R.id.n9);
        Button n0 = (Button) findViewById(R.id.n0);

        Button dot = (Button) findViewById(R.id.dot);
        Button del = (Button) findViewById(R.id.del);
        Button eql = (Button) findViewById(R.id.eql);

        Button pls = (Button) findViewById(R.id.pls);
        Button min = (Button) findViewById(R.id.min);
        Button per = (Button) findViewById(R.id.per);
        Button div = (Button) findViewById(R.id.div);

        n1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(1);
            }
        });
        n2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(2);
            }
        });
        n3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(3);
            }
        });
        n4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(4);
            }
        });
        n5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(5);
            }
        });
        n6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(6);
            }
        });
        n7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(7);
            }
        });
        n8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(8);
            }
        });
        n9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(9);
            }
        });
        n0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onNumberClick(0);
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if((step==1 || step==3) && !decimal) {
                    addToDisplay(".");
                    decimal = true;
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(step!=3){
                    step = 0;
                } else {
                    step = 2;
                }
                cleanDisplay();
            }
        });

        eql.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(step==3){
                    second_number = getByDisplay();
                    cleanDisplay();
                    step = 4;
                    addToDisplay("" + calculate());
                }
            }
        });

        pls.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExpressionClick(0);
            }
        });
        min.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExpressionClick(1);
            }
        });
        per.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExpressionClick(2);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExpressionClick(3);
            }
        });

    }

    private void onNumberClick(int value){
        addToDisplay("" + value);
        if(step==4){
            step = 1;
        } else if(step==0 || step==2){
            step++;
        }
    }

    private void onExpressionClick(int code){
        if(step==0 || step==1){
            first_number = getByDisplay();
            expression_code = code;
            step = 2;
            cleanDisplay();
        } else if(step==2){
            expression_code = code;
        } else if(step==3){
            second_number = getByDisplay();
            first_number = calculate();
            expression_code = code;
            step = 2;
            addToDisplay("" + first_number);
        } else if(step==4){
            first_number = getByDisplay();
            expression_code = code;
            step = 2;
            cleanDisplay();
            addToDisplay("" + first_number);
        }

    }

    private float calculate(){
        float result = 0;
        if(expression_code==0){
            result = first_number + second_number;
        } else if(expression_code==1){
            result = first_number - second_number;
        } else if(expression_code==2){
            result = first_number * second_number;
        } else if(expression_code==3){
            result = first_number / second_number;
        }
        return result;
    }

    private void addToDisplay(String s){
        TextView display = (TextView) findViewById(R.id.display);
        if(step==1 || step==3){
            s = display.getText() + s;
        }
        if(s.length()>=6 && s.length()<=14){
            display.setTextSize(30);
        } else if(s.length()>14){
            s = s.substring(0, 14);
        }
        display.setText(s);
    }

    private void cleanDisplay(){
        TextView display = (TextView) findViewById(R.id.display);
        display.setText("0.0");
        decimal = false;
    }

    private float getByDisplay(){
        TextView display = (TextView) findViewById(R.id.display);
        return Float.parseFloat(display.getText().toString());
    }
}
