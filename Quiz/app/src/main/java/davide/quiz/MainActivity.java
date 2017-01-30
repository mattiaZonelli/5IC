package davide.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUESTTWO = 0;
    public static final int REQUESTTHREE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Quiz1 = (Button) findViewById(R.id.quiz1);
        Quiz1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityTwo.class);
                startActivityForResult(i, REQUESTTWO);
            }
        });

        Button Quiz2 = (Button) findViewById(R.id.quiz2);
        Quiz2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityThree.class);
                startActivityForResult(i, REQUESTTHREE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUESTTWO){
            if (resultCode == RESULT_OK){
                String finish = data.getStringExtra("true");
                if(finish.equals("0"))
                    Toast.makeText(this, "Quiz sui computer completato con successo! Prova l'altro quiz!", Toast.LENGTH_LONG).show();
                if (finish.equals("1")){
                    Toast.makeText(this, "Hai sbagliato alcune risposte, ritenta sarai più fortunato", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQUESTTHREE){
            if (resultCode == RESULT_OK){
                String finish = data.getStringExtra("true");
                if(finish.equals("0"))
                    Toast.makeText(this, "Quiz sullo sport completato con successo! Prova l'altro quiz!", Toast.LENGTH_LONG).show();
                if (finish.equals("1")){
                    Toast.makeText(this, "Hai sbagliato alcune risposte, ritenta sarai più fortunato", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
