package com.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.calculator.parser.ExpressionParser;

import java.util.Locale;

public class MainActivity extends Activity {

    private static String operations;
    private Number lastResult;
    private int resize = 5;
    private int resizeConstant = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((EditText) findViewById(R.id.display)).setMaxLines(7);
        if (savedInstanceState != null) {
            operations = savedInstanceState.getString("OPERATIONS");
            lastResult = savedInstanceState.getDouble("LAST_RESULT");
            setTextSize((EditText) findViewById(R.id.display));
        } else {
            lastResult = null;
            operations = "";
        }
        initListeners();
    }

    private void initListeners() {
        findViewById(R.id.one).setOnClickListener(new ButtonListener(R.id.one));
        findViewById(R.id.two).setOnClickListener(new ButtonListener(R.id.two));
        findViewById(R.id.three).setOnClickListener(new ButtonListener(R.id.three));
        findViewById(R.id.four).setOnClickListener(new ButtonListener(R.id.four));
        findViewById(R.id.six).setOnClickListener(new ButtonListener(R.id.six));
        findViewById(R.id.five).setOnClickListener(new ButtonListener(R.id.five));
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
        findViewById(R.id.CE).setOnClickListener(new ButtonListener(R.id.CE));
    }

    private class ButtonListener implements View.OnClickListener {

        private int code;
        EditText et = (EditText) findViewById(R.id.display);

        private ButtonListener(int code) {
            this.code = code;
            et.setTextSize(85);
        }

        @Override
        public void onClick(View v) {
            if (code != R.id.equals) {
                if (code == R.id.CE) {
                    operations = "";
                    lastResult = null;
                } else if (code == R.id.delete) {
                    if (operations.length() > 0) {
                        operations = operations.substring(0, operations.length() - 1);
                    }
                } else {
                    if (code == R.id.pi) {
                        operations += "\u03C0";
                    } else if (code == R.id.neper) {
                        operations += "\u2107";
                    } else {
                        operations += ((Button) findViewById(code)).getText().toString();
                    }
                }
            } else {
                if (operations.startsWith("-") || operations.startsWith("+")) {
                    operations = "0" + operations;
                }
                compute(operations);
                if (lastResult == null) {
                    operations = "Error";
                    lastResult = null;
                } else {
                    operations = "" + (operations.length() > 7 ? String.format(Locale.getDefault(), "%2.2E", lastResult) : lastResult);
                }
                resizeConstant=60;
                resize=5;
            }
            setTextSize(et);
            et.setText(operations);
            Bundle bundle = new Bundle();

            onSaveInstanceState(bundle);
            et.setSelection(et.length());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("OPERATIONS", operations);
        outState.putDouble("LAST_RESULT", lastResult == null ? 0.0 : lastResult.doubleValue());
    }

    private void setTextSize(EditText et) {
        if (operations.length() % resize == 0 || (lastResult != null && lastResult.toString().equals(operations))) {
            if (operations.length() != 0) {
                if (et.getLineCount() > et.getMaxLines()) {
                    resizeConstant = 40;
                    resize = 3;
                }
            }
            et.setTextSize((float) (resizeConstant / Math.log10(operations.length() == 1 || operations.length() == 0 ? 2 : operations.length())));

        }
    }

    private Number compute(String operations) {
        ExpressionParser parser = new ExpressionParser(operations, lastResult);
        lastResult = parser.parse();
        if (lastResult != null) {
            if (Math.signum(lastResult.doubleValue()) * (lastResult.doubleValue() - lastResult.intValue()) > 0) {
                lastResult = new Double(lastResult.doubleValue());
            } else {
                lastResult = new Integer(lastResult.intValue());
            }
        }
        return lastResult;
    }


}
