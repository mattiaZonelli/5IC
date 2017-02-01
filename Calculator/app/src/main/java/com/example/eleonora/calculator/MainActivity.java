package com.example.eleonora.calculator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
        findViewById(R.id.uno).setOnClickListener(new ButtonListener(R.id.uno));
        findViewById(R.id.due).setOnClickListener(new ButtonListener(R.id.due));
        findViewById(R.id.tre).setOnClickListener(new ButtonListener(R.id.tre));
        findViewById(R.id.quattro).setOnClickListener(new ButtonListener(R.id.quattro));
        findViewById(R.id.sei).setOnClickListener(new ButtonListener(R.id.sei));
        findViewById(R.id.cinque).setOnClickListener(new ButtonListener(R.id.cinque));
        findViewById(R.id.sette).setOnClickListener(new ButtonListener(R.id.sette));
        findViewById(R.id.otto).setOnClickListener(new ButtonListener(R.id.otto));
        findViewById(R.id.nove).setOnClickListener(new ButtonListener(R.id.nove));
        findViewById(R.id.zero).setOnClickListener(new ButtonListener(R.id.zero));
        findViewById(R.id.punto).setOnClickListener(new ButtonListener(R.id.punto));
        findViewById(R.id.somma).setOnClickListener(new ButtonListener(R.id.somma));
        findViewById(R.id.sottrazione).setOnClickListener(new ButtonListener(R.id.sottrazione));
        findViewById(R.id.moltiplicazione).setOnClickListener(new ButtonListener(R.id.moltiplicazione));
        findViewById(R.id.divisione).setOnClickListener(new ButtonListener(R.id.divisione));
        findViewById(R.id.uguale).setOnClickListener(new ButtonListener(R.id.uguale));
        findViewById(R.id.delete).setOnClickListener(new ButtonListener(R.id.delete));

    }

    private class ButtonListener implements View.OnClickListener {

        private int code;
        TextView display = (TextView) findViewById(R.id.display);

        private ButtonListener(int code) {
            this.code = code;
        }

        @Override
        public void onClick(View v) {
            if (code != R.id.uguale) {
                if (code == R.id.delete) {
                    if(operations.length()>0){
                        operations = operations.substring(0, operations.length() - 1);
                    }
                } else {

                    operations += ((Button) findViewById(code)).getText().toString();
                }

            } else {
                compute(operations);
                if(lastResult==null){
                    operations="Error";
                    lastResult=null;
                }else {
                    operations = "" + lastResult;
                }
            }

            display.setText(operations);
            display.setText(operations);

        }
    }




    private Number compute(String operations) {
        ExpressionParsers parser = new ExpressionParsers(operations);
        lastResult = parser.parse();
        if(lastResult!=null) {
            if (lastResult.doubleValue() - lastResult.intValue() > 0) {
                lastResult = new Double(lastResult.doubleValue());
            } else {
                lastResult = new Integer(lastResult.intValue());
            }
        }
        return lastResult;
    }

}
