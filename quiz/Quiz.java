package mariano.mazzucchi.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        LinearLayout list = (LinearLayout) findViewById(R.id.listLayout);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDataBase();
        SQLiteDatabase database = dbHelper.getDataBase();

        Cursor listaQuiz = database.rawQuery("SELECT NomeQuiz FROM ListaQuiz", null);

        while (listaQuiz.moveToNext()){

            Button button = new Button(this);
            button.setText(listaQuiz.getString(0));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNewActivityQuiz(((Button)v).getText().toString());
                }
            });
            list.addView(button);


        }

        database.close();
    }
    private void openNewActivityQuiz(String nomeQuiz){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDataBase();
        SQLiteDatabase database = dbHelper.getDataBase();
        Cursor idQuizCursor = database.rawQuery("SELECT _id FROM ListaQuiz WHERE ListaQuiz.NomeQuiz ='" + nomeQuiz +"'", null);
        int idQuiz = -1;

        while (idQuizCursor.moveToNext()){
            idQuiz = idQuizCursor.getInt(0);
        }

        dbHelper.close();

        Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
        quizIntent.putExtra("idQuiz", idQuiz);
        MainActivity.this.startActivityForResult(quizIntent, 1);

    }


    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {

        if (requestCode == 1) {

            int punteggio = data.getIntExtra("punteggio",0);

            if(punteggio >= 0){
                String s = "Punteggio: "+String.valueOf(punteggio);
                Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
            } else if(punteggio == -2){
                String s = "Quiz annullato";
                Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
            }
            else {
                String s = "Questo quiz non è ancora disponibile";
                Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
            }


        }

    }
}
