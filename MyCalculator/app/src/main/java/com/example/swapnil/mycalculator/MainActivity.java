package com.example.swapnil.mycalculator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.swapnil.mycalculator.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main);

        binding.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + ".";
                binding.resultEditText.setText(binding.resultEditText.getText() + ".");

            }
        });

        binding.btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "0";
                binding.resultEditText.setText(binding.resultEditText.getText() + "0");

            }
        });

        binding.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "1";
                binding.resultEditText.setText(binding.resultEditText.getText() + "1");

            }
        });

        binding.btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "2";
                binding.resultEditText.setText(binding.resultEditText.getText() + "2");

            }
        });

        binding.btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "3";
                binding.resultEditText.setText(binding.resultEditText.getText() + "3");

            }
        });

        binding.btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "4";
                binding.resultEditText.setText(binding.resultEditText.getText() + "4");

            }
        });

        binding.btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "5";
                binding.resultEditText.setText(binding.resultEditText.getText() + "5");

            }
        });

        binding.btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "6";
                binding.resultEditText.setText(binding.resultEditText.getText() + "6");

            }
        });

        binding.btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "7";
                binding.resultEditText.setText(binding.resultEditText.getText() + "7");

            }
        });

        binding.btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "8";
                binding.resultEditText.setText(binding.resultEditText.getText() + "8");

            }
        });

        binding.btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "9";
                binding.resultEditText.setText(binding.resultEditText.getText() + "9");

            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "+";
                binding.resultEditText.setText(binding.resultEditText.getText() + "+");
            }
        });

        binding.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "-";
                binding.resultEditText.setText(binding.resultEditText.getText() + "-");
            }
        });

        binding.btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "*";
                binding.resultEditText.setText(binding.resultEditText.getText() + "*");
            }
        });

        binding.btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = expression + "/";
                binding.resultEditText.setText(binding.resultEditText.getText() + "/");
            }
        });


        binding.btnEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Object result = eval(expression);
               binding.infoTextView.setText(result.toString());
            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression = "";
                    binding.resultEditText.setText("");
                    binding.infoTextView.setText("");
            }
        });

    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

}
