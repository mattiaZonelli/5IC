package com.example.matteo.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityThree extends AppCompatActivity {

    TextView result;

    private final String risposteCorrette = "14";
    String risposteDate = "";
    Button btnTermina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        btnTermina = (Button) findViewById(R.id.buttonTermina);
        result = (TextView) findViewById(R.id.result);
        result.setEnabled(false);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;


        btnTermina.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                result.setText(getResult());

                Intent i = new Intent();
                i.putExtra("isOk",  getResult());
                ActivityThree.this.setResult(RESULT_OK, i);
                ActivityThree.this.finish();
            }
        });
    }

    public String getResult() {
        String res;
        if (risposteCorrette.equals(risposteDate)) res = "Test corretto";
        else res = "Test errato";
        return res;

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioGroup myRadioGroup = (RadioGroup) findViewById(R.id.yourRadioGroup);
        //int index = myRadioGroup.indexOfChild(findViewById(myRadioGroup.getCheckedRadioButtonId()));


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio11:
                if (checked) {
                    risposteDate = risposteDate + "1";
                    result.setText(risposteDate);
                    result.setEnabled(true);
                }
                break;
            case R.id.radio12:
                if (checked) {
                    risposteDate = risposteDate + "2";

                    result.setText(risposteDate);
                    result.setEnabled(true);
                }
                break;
            case R.id.radio13:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "3";
                }
                break;
            case R.id.radio14:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "4";
                }
                break;
            case R.id.radio21:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "1";
                }
                break;
            case R.id.radio22:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "2";
                }
                break;
            case R.id.radio23:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "3";
                }
                break;
            case R.id.radio24:
                if (checked) {
                    result.setText(risposteDate);
                    result.setEnabled(true);
                    risposteDate = risposteDate + "4";
                }
                break;

        }

        result.setText(risposteDate);

    }
}
