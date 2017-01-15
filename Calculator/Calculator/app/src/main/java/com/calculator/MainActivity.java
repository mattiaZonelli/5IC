package com.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.calculator.utils.binarytree.BinaryTree;

public class MainActivity extends AppCompatActivity {

    private static String operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        operations="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



    }

    private class ButtonListener implements View.OnClickListener{

        private int code;
        private ButtonListener (int code){
           this.code=code;
        }
        @Override
        public void onClick(View v) {
            if(code!=R.id.equals) {
                operations += ((Button) findViewById(code)).getText().toString();
                EditText et=(EditText)findViewById(R.id.display);
                et.setText(operations);
               et.setSelection(et.length());
            }
            else{
                compute(operations);
                operations="";
            }
        }
    }

    private String compute(String operations){
        inspect(operations);
        BinaryTree parsingTree=new BinaryTree();
        return null;
    }

    private void inspect(String operations) {

    }

}
