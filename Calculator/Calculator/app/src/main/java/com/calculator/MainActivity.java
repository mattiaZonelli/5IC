package com.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.calculator.parser.ExpressionParser;

public class MainActivity extends AppCompatActivity {

    private static String operations;
    private Number lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        operations = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastResult = null;
        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.one).setOnClickListener(new ButtonListener(R.id.one));
        findViewById(R.id.two).setOnClickListener(new ButtonListener(R.id.two));
        findViewById(R.id.three).setOnClickListener(new ButtonListener(R.id.three));
        findViewById(R.id.four).setOnClickListener(new ButtonListener(R.id.four));
        findViewById(R.id.five).setOnClickListener(new ButtonListener(R.id.five));
        findViewById(R.id.six).setOnClickListener(new ButtonListener(R.id.six));
        findViewById(R.id.seven).setOnClickListener(new ButtonListener(R.id.seven));
        findViewById(R.id.eight).setOnClickListener(new ButtonListener(R.id.eight));
        findViewById(R.id.nine).setOnClickListener(new ButtonListener(R.id.nine));
        findViewById(R.id.zero).setOnClickListener(new ButtonListener(R.id.zero));
        findViewById(R.id.point).setOnClickListener(new ButtonListener(R.id.point));
        findViewById(R.id.plus).setOnClickListener(new ButtonListener(R.id.plus));
        findViewById(R.id.minus).setOnClickListener(new ButtonListener(R.id.minus));
        findViewById(R.id.per).setOnClickListener(new ButtonListener(R.id.per));
        findViewById(R.id.slash).setOnClickListener(new ButtonListener(R.id.slash));
        findViewById(R.id.equals).setOnClickListener(new ButtonListener(R.id.equals));
        findViewById(R.id.delete).setOnClickListener(new ButtonListener(R.id.delete));
        findViewById(R.id.sqrt).setOnClickListener(new ButtonListener(R.id.sqrt));
        findViewById(R.id.pi).setOnClickListener(new ButtonListener(R.id.pi));
        findViewById(R.id.neper).setOnClickListener(new ButtonListener(R.id.neper));
        findViewById(R.id.exp).setOnClickListener(new ButtonListener(R.id.exp));

    }

    private class ButtonListener implements View.OnClickListener {

        private int code;
        EditText et = (EditText) findViewById(R.id.display);

        private ButtonListener(int code) {
            this.code = code;
        }

        @Override
        public void onClick(View v) {
            if (code != R.id.equals) {
                if (code == R.id.delete) {
                    if(operations.length()>0){ //problema se cancello numero già presente
                        operations = operations.substring(0, operations.length() - 1);
                    }
                } else {
                    if(code==R.id.pi){
                        operations+=Math.PI;
                    }else if(code==R.id.neper){
                        operations+=Math.E;
                    }else {
                        operations += ((Button) findViewById(code)).getText().toString();
                    }
                }
            } else {
                compute(operations);
                operations = "" + lastResult;
            }
            setTextSize(et);
            et.setText(operations);
            et.setSelection(et.length());
        }
    }

    private void setTextSize(EditText et){
        if(operations.length()<=4){
          et.setTextSize(getResources().getDimension(R.dimen.largeText));
        }else if(operations.length()<=8){
            et.setTextSize(getResources().getDimension(R.dimen.mediumText));
        }else if (operations.length()<=12){
            et.setTextSize(getResources().getDimension(R.dimen.smallText));
        }else{
            et.setTextSize(getResources().getDimension(R.dimen.xsmallText));
        }
    }

    private Number compute(String operations) {
        ExpressionParser parser = new ExpressionParser(operations, lastResult);
        lastResult = parser.parse();
        if(lastResult.doubleValue()-lastResult.intValue()>0) {
            lastResult=new Double(lastResult.doubleValue());
        }else{
            lastResult=new Integer(lastResult.intValue());
        }
        return lastResult;
    }



}
