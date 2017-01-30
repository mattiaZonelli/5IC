package davide.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ActivityThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        Button confirm = (Button)findViewById(R.id.Confirm3);
        final RadioButton A21T = (RadioButton) findViewById(R.id.A21T);
        final RadioButton A21F = (RadioButton) findViewById(R.id.A21F);
        final RadioButton A22T = (RadioButton) findViewById(R.id.A22T);
        final RadioButton A22F = (RadioButton) findViewById(R.id.A22F);
        final RadioButton A23T = (RadioButton) findViewById(R.id.A23T);
        final RadioButton A23F = (RadioButton) findViewById(R.id.A23F);
        final RadioButton A24T = (RadioButton) findViewById(R.id.A24T);
        final RadioButton A24F = (RadioButton) findViewById(R.id.A24F);
        final RadioButton A25T = (RadioButton) findViewById(R.id.A25T);
        final RadioButton A25F = (RadioButton) findViewById(R.id.A25F);
        final RadioButton A26T = (RadioButton) findViewById(R.id.A26T);
        final RadioButton A26F = (RadioButton) findViewById(R.id.A26F);
        final RadioButton A27T = (RadioButton) findViewById(R.id.A27T);
        final RadioButton A27F = (RadioButton) findViewById(R.id.A27F);

        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (A21T.isChecked() && A22F.isChecked() && A23T.isChecked() && A24F.isChecked() && A25T.isChecked()
                        && A26F.isChecked() && A27F.isChecked()){
                    Intent i = new Intent();
                    i.putExtra("true", "0");
                    ActivityThree.this.setResult(RESULT_OK, i);
                    ActivityThree.this.finish();
                } else{
                    Intent j = new Intent();
                    j.putExtra("true","1");
                    ActivityThree.this.setResult(RESULT_OK, j);
                    ActivityThree.this.finish();
                }

            }
        });





    }
}
