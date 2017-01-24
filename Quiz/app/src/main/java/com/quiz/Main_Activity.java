package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Activity extends Activity {

    private int NUMBER_OF_QUESTIONS = 1;
    public String questions[][] = new String[NUMBER_OF_QUESTIONS][FeedReaderDbHelper.numberOfColumns()];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newActivity=(Button)(findViewById(R.id.newActivityButton));
        Button quit=(Button)(findViewById(R.id.quitButton));
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main_Activity.this,QuestionActivity.class);
                startActivity(intent);
                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(Main_Activity.this.getApplicationContext());
                String values[] = {"1", "Quale dei seguenti è un colore primario?", "Rosso", "Arancio", "Verde", "Viola", "1"};
                //mDbHelper.insert(mDbHelper,values);

                for (int i = 0; i < questions.length; i++) {
                    Cursor cursor = mDbHelper.read(mDbHelper, (int) ((Math.random() * questions.length) + 1));

                    int index = 0;
                    cursor.moveToFirst();
                    cursor.moveToPosition(0);
                    while (index < cursor.getColumnCount()) {
                        String itemId = cursor.getString(
                                cursor.getColumnIndex(FeedReaderDbHelper.getField(index)));
                        questions[i][index] = itemId;

                        index++;
                    }


                    cursor.close();
                }

                mDbHelper.close();
            }
        });
    }
}
