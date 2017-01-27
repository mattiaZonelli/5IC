package fabiomanfrin.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Menu extends Activity{


    private TextView risultato_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button begin_button=(Button) findViewById(R.id.begin_button);
        begin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner=(Spinner) findViewById(R.id.spinner);
                Intent i = new Intent(Menu.this,Question.class);
                i.putExtra("difficulty",spinner.getSelectedItem().toString());
                startActivityForResult(i,1);


            }
        });
    }


        public void onActivityResult(int requestCode, int resultCode, Intent data) {

                TextView t = (TextView) findViewById(R.id.nCorrette);
                TextView tScroll = (TextView) findViewById(R.id.text_spinner);
                String risultato = data.getExtras().getString("l");

                System.out.println(risultato);

                tScroll.setText(risultato);
                int corrette = data.getExtras().getInt("c");
            int size = data.getExtras().getInt("size");
                t.setText("corrette: " + corrette+"/"+size);


            }

    }

