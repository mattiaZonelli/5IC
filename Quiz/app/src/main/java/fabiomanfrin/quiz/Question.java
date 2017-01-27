package fabiomanfrin.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Question extends Activity {

    private int nDomanda=0;
    private int nCorrette=0;
    private ArrayList <String> answer=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

       final TextView t=(TextView) findViewById(R.id.textView);
        Button True=(Button) findViewById(R.id.true_button);
        Button False=(Button) findViewById(R.id.false_button);
        Button Finish=(Button) findViewById(R.id.finish_button);
        DBHelper helper=DBHelper.getInstance(this.getApplicationContext());
        helper.openDataBase();
        SQLiteDatabase database=helper.getReadableDatabase();

        String difficulty=getIntent().getStringExtra("difficulty");
        String q="SELECT * FROM Questions WHERE Difficulty='"+difficulty+"';";
        Cursor cursor=database.rawQuery(q,null);
        cursor.moveToFirst();

        final ArrayList<Tupla> questions=new ArrayList<>();

        while(!cursor.isAfterLast()){
            questions.add(new Tupla(cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }

        t.setText(questions.get(nDomanda).getQuestion());
            True.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nDomanda == questions.size()) {
                        Toast.makeText(Question.this, "Quiz finito! clicca Finish per il risultato", Toast.LENGTH_SHORT).show();
                    } else{

                        if (questions.get(nDomanda).getAnswer().equals("T")) {
                            answer.add("corretta");
                            nCorrette++;
                            nDomanda++;
                        } else {
                            answer.add("errata");
                            nDomanda++;
                        }
                        if (nDomanda < questions.size())
                        t.setText(questions.get(nDomanda).getQuestion());

                    }

                }
            });

            False.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nDomanda == questions.size()) {
                        Toast.makeText(Question.this, "Quiz finito! clicca Finish per il risultato", Toast.LENGTH_SHORT).show();
                    } else {

                        if (questions.get(nDomanda).getAnswer().equals("F")) {
                            answer.add("corretta");
                            nCorrette++;
                            nDomanda++;

                        } else {
                            answer.add("errata");
                            nDomanda++;
                        }
                        if (nDomanda < questions.size())
                        t.setText(questions.get(nDomanda).getQuestion());

                    }

                }
            });





        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Risultato r=new Risultato(questions,answer,nCorrette);
                if(!(answer.size()<questions.size())){
                String sRisultato=r.getRisultato();
                    Intent intent=new Intent(Question.this,Menu.class);
                Bundle extras=new Bundle();
                extras.putInt("c",nCorrette);
                extras.putString("l",sRisultato);
                    extras.putInt("size",questions.size());
                intent.putExtras(extras);

                    Question.this.setResult(2, intent);
                    Question.this.finish();

                }
            }
        });

    }


}
