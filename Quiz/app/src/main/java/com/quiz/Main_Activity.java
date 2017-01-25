package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main_Activity extends Activity {

    private int NUMBER_OF_QUESTIONS = 5;
    public String questions[][] = new String[NUMBER_OF_QUESTIONS][FeedReaderDbHelper.numberOfColumns()];
    public ArrayList<Integer> foundIDs=new ArrayList<>(NUMBER_OF_QUESTIONS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newActivity = (Button) (findViewById(R.id.newActivityButton));
        Button quit = (Button) (findViewById(R.id.quitButton));
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, QuestionActivity.class);
                startActivity(intent);
                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(Main_Activity.this.getApplicationContext());
             /*   try {
                    addTupleFromCsv(mDbHelper, getAssets().open("file.csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

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

                Bundle bundle=new Bundle();
                bundle.putSerializable("key",questions);
                Intent i=new Intent(Main_Activity.this,QuestionActivity.class);
                i.putExtras(bundle);
                startActivityForResult(i,2);

            }
        });
    }

    private void addTupleFromCsv(FeedReaderDbHelper mDbHelper, InputStream path) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(path));
            String line = " ";
            while ((line = bf.readLine()) != null) {
                String values[] = line.split("§");
                String toBeInserted[] = Arrays.copyOf(values, values.length + 1);
                toBeInserted[toBeInserted.length - 1] = "1";
                System.out.println(Arrays.toString(toBeInserted));
                mDbHelper.insert(mDbHelper, toBeInserted);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
