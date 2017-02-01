package com.gallez.pool.calculator;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView expr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = (TextView) findViewById(R.id.Expression);
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button)view;
                String buttonTxt = button.getText().toString();
                String exprTxt = expr.getText().toString();
                if(buttonTxt.equals("CE")) {
                    expr.setText("0");
                }
                else
                if(exprTxt.equals("0"))
                    expr.setText(buttonTxt);
                else
                    expr.append(buttonTxt);
            }
        };
        findViewById(R.id.zero).setOnClickListener(buttonListener);
        findViewById(R.id.one).setOnClickListener(buttonListener);
        findViewById(R.id.two).setOnClickListener(buttonListener);
        findViewById(R.id.tree).setOnClickListener(buttonListener);
        findViewById(R.id.four).setOnClickListener(buttonListener);
        findViewById(R.id.five).setOnClickListener(buttonListener);
        findViewById(R.id.six).setOnClickListener(buttonListener);
        findViewById(R.id.seven).setOnClickListener(buttonListener);
        findViewById(R.id.eight).setOnClickListener(buttonListener);
        findViewById(R.id.nine).setOnClickListener(buttonListener);
        findViewById(R.id.comma).setOnClickListener(buttonListener);
        findViewById(R.id.plus).setOnClickListener(buttonListener);
        findViewById(R.id.minus).setOnClickListener(buttonListener);
        findViewById(R.id.asterisk).setOnClickListener(buttonListener);
        findViewById(R.id.slash).setOnClickListener(buttonListener);
        findViewById(R.id.CE).setOnClickListener(buttonListener);
        findViewById(R.id.equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView resultArea = (TextView) findViewById(R.id.Expression);
                String expression = Postfixer.removeCommas(resultArea.getText().toString());
                if (expression.charAt(0) == '+' || expression.charAt(0) == '-')
                    expression = "0" + expression;
                String exprWithSpaces = Postfixer.prepareInfix(expression);
                if (Postfixer.verifyExpr(exprWithSpaces)) {
                    String postfixed = Postfixer.postfix(exprWithSpaces);
                    String result = Postfixer.calculate(postfixed).toString();
                    if (result.substring(result.indexOf(".") + 1, result.length()).equals("0"))
                        expr.setText(result.substring(0, result.indexOf(".")));
                    else
                        expr.setText(result);
                } else {
                    Toast.makeText(getBaseContext(), "L'equazione non è corretta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        expr.setText(savedInstanceState.getString("DISPLAY_CONTENT"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("DISPLAY_CONTENT",expr.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
