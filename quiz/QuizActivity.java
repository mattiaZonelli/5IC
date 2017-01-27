package mariano.mazzucchi.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import java.io.IOException;


public class QuizActivity extends AppCompatActivity {

    private int punteggio = 0;

    private String[] listaDomande;
    private int[] listaDomandeID;
    private int[] punteggioArray;
    private int domandaCorrente;
    private boolean selezionataCorretta = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        int idQuiz = getIntent().getIntExtra("idQuiz", -1);

        loadQuiz(idQuiz);
    }

    private void loadQuiz(int idQuiz){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDataBase();
        SQLiteDatabase database = dbHelper.getDataBase();
        Cursor listaDomandeCursor = database.rawQuery("SELECT IDDomanda FROM DomandaQuiz WHERE DomandaQuiz.IDQuiz = " + idQuiz, null);
        listaDomande = new String[listaDomandeCursor.getCount()];
        listaDomandeID = new int[listaDomandeCursor.getCount()];
        punteggioArray = new int[listaDomandeCursor.getCount()];

        for (int i = 0; i < punteggioArray.length; i++) {
            punteggioArray[i] = 0;
        }

        int i = 0;
        while (listaDomandeCursor.moveToNext()){

            Cursor domandaCursor = database.rawQuery("SELECT Domanda FROM Domanda WHERE Domanda._id = " + listaDomandeCursor.getInt(0), null);
            listaDomandeID[i] = listaDomandeCursor.getInt(0);
            while (domandaCursor.moveToNext()){
                listaDomande[i] = domandaCursor.getString(0);
            }

            i++;
        }

        dbHelper.close();

        if(listaDomande.length > 0){
            domandaCorrente = 0;
            loadDomanda(listaDomandeID[domandaCorrente], listaDomande[domandaCorrente]);
        } else {
            punteggio = -1;
            endQuiz();
        }


    }

    private void loadDomanda(int idDomanda, final String domanda) {

        System.out.println("Domanda corrente " + domandaCorrente);

        TextView tvDomanda = (TextView) findViewById(R.id.tvDomanda);

        tvDomanda.setText(domanda);
        LinearLayout listaRisposte = (LinearLayout) findViewById(R.id.listaRisposte);
        LinearLayout listaBottoni = (LinearLayout) findViewById(R.id.listaBottoni);

        listaRisposte.removeAllViews();
        listaBottoni.removeAllViews();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper.openDataBase();
        SQLiteDatabase database = dbHelper.getDataBase();

        Cursor tipoDomandaCursor = database.rawQuery("SELECT TipoQuiz FROM TipoDomanda WHERE TipoDomanda._id = (SELECT TipoDomanda FROM Domanda WHERE Domanda._id = " + idDomanda + ")", null);
        String tipoDomanda = "";
        while (tipoDomandaCursor.moveToNext()) {
            tipoDomanda = tipoDomandaCursor.getString(0);
        }


        Cursor listaRisposteCursor = database.rawQuery("SELECT Risposta, Corretta FROM Risposte WHERE Risposte.IDDomanda=" + idDomanda, null);

        if (tipoDomanda.equals("VEROFALSO")) {

            while (listaRisposteCursor.moveToNext()) {
                Button button = new Button(this);
                button.setText(listaRisposteCursor.getString(0));
                button.setTextColor(Color.WHITE);
                if(listaRisposteCursor.getString(0).equals("VERO")){
                    button.setBackgroundColor(Color.rgb(40, 122, 45));
                } else {
                    button.setBackgroundColor(Color.rgb(163, 16, 16));
                }
                if (listaRisposteCursor.getInt(1) == 1) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            punteggioArray[domandaCorrente] = 1;
                            if (domandaCorrente < listaDomande.length - 1) {
                                domandaCorrente++;
                                loadDomanda(listaDomandeID[domandaCorrente], listaDomande[domandaCorrente]);
                            } else {
                                endQuiz();
                            }
                        }
                    });
                } else {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            punteggioArray[domandaCorrente] = 0;
                            if (domandaCorrente < listaDomande.length - 1) {
                                domandaCorrente++;
                                loadDomanda(listaDomandeID[domandaCorrente], listaDomande[domandaCorrente]);
                            } else {
                                endQuiz();
                            }
                        }
                    });
                }
                button.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                listaBottoni.addView(button);
            }


        } else if (tipoDomanda.equals("MULTIPLA")) {
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(LinearLayout.VERTICAL);
            while (listaRisposteCursor.moveToNext()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(listaRisposteCursor.getString(0));
                if (listaRisposteCursor.getInt(1) == 1) {
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selezionataCorretta = true;
                        }
                    });
                } else {
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selezionataCorretta = false;
                        }
                    });
                }



                radioGroup.addView(radioButton);
            }

            Button button = new Button(this);
            button.setText("Conferma");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selezionataCorretta) {
                        punteggioArray[domandaCorrente] = 1;
                    } else {
                        punteggioArray[domandaCorrente] = 0;
                    }
                    if (domandaCorrente < listaDomande.length - 1) {
                        domandaCorrente++;
                        loadDomanda(listaDomandeID[domandaCorrente], listaDomande[domandaCorrente]);
                    } else {
                        endQuiz();
                    }
                }
            });
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            listaRisposte.addView(radioGroup);
            listaBottoni.addView(button);


        }

        dbHelper.close();
    }

    @Override
    public void onBackPressed(){
        System.out.println("Domanda corrente " + domandaCorrente);
        if(domandaCorrente == 0){
            for (int i = 0; i < this.punteggioArray.length; i++) {
                this.punteggioArray[i] = 0;
            }

            punteggio = -2;
            endQuiz();
        } else {
            domandaCorrente--;
            loadDomanda(listaDomandeID[domandaCorrente], listaDomande[domandaCorrente]);
        }



    }

    private void endQuiz(){

        for (int i = 0; i < this.punteggioArray.length; i++) {
            punteggio += this.punteggioArray[i];
        }

        Intent output = new Intent();
        output.putExtra("punteggio", punteggio);
        setResult(RESULT_OK, output);
        finish();
    }
}
