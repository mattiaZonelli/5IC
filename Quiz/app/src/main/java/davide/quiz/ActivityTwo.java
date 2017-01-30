package davide.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Button confirm = (Button)findViewById(R.id.Confirm2);
        final RadioButton A1T = (RadioButton) findViewById(R.id.A1T);
        final RadioButton A1F = (RadioButton) findViewById(R.id.A1F);
        final RadioButton A2T = (RadioButton) findViewById(R.id.A2T);
        final RadioButton A2F = (RadioButton) findViewById(R.id.A2F);
        final RadioButton A3T = (RadioButton) findViewById(R.id.A3T);
        final RadioButton A3F = (RadioButton) findViewById(R.id.A3F);
        final RadioButton A4T = (RadioButton) findViewById(R.id.A4T);
        final RadioButton A4F = (RadioButton) findViewById(R.id.A4F);
        final RadioButton A5T = (RadioButton) findViewById(R.id.A5T);
        final RadioButton A5F = (RadioButton) findViewById(R.id.A5F);
        final RadioButton A6T = (RadioButton) findViewById(R.id.A6T);
        final RadioButton A6F = (RadioButton) findViewById(R.id.A6F);
        final RadioButton A7T = (RadioButton) findViewById(R.id.A7T);
        final RadioButton A7F = (RadioButton) findViewById(R.id.A7F);

        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (A1T.isChecked() && A2F.isChecked() && A3F.isChecked() && A4T.isChecked() && A5F.isChecked()
                        && A6T.isChecked() && A7T.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("true", "0");
                    ActivityTwo.this.setResult(RESULT_OK, i);
                    ActivityTwo.this.finish();
                } else{
                    Intent j = new Intent();
                    j.putExtra("true","1");
                    ActivityTwo.this.setResult(RESULT_OK, j);
                    ActivityTwo.this.finish();
                }

            }
        });





    }
}


