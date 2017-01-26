package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main_Activity extends Activity {

    private final int ANSWER_1 = 2;
    private final int ANSWER_2 = 3;
    private final int ANSWER_3 = 4;
    private final int ANSWER_4 = 5;
    private int NUMBER_OF_QUESTIONS = 12;
    public String questions[][] = new String[NUMBER_OF_QUESTIONS][FeedReaderDbHelper.numberOfColumns()];
    public ArrayList<Integer> foundIDs = new ArrayList<>(NUMBER_OF_QUESTIONS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newActivity = (Button) (findViewById(R.id.newActivityButton));
        Button quit = (Button) (findViewById(R.id.quitButton));

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, QuestionActivity.class);
                startActivity(intent);
                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(Main_Activity.this.getApplicationContext());

                /*  if (getDatabasePath("/data/data/com.quiz/databases/QuizDatabase.sqlite").exists()) {
                    try {
                        addTupleFromCsv(mDbHelper, getAssets().open("file.csv"));
                        System.out.println("DATABASE ADDED");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/

                for (int i = 0; i < questions.length; i++) {
                    Cursor cursor = mDbHelper.read(mDbHelper, newRandom());
                    int index = 0;
                    cursor.moveToFirst();
                    while (index < cursor.getColumnCount()) {
                        String itemId = cursor.getString(
                                cursor.getColumnIndex(FeedReaderDbHelper.getField(index)));
                        questions[i][index] = itemId;

                        index++;
                    }

                    cursor.close();
                }

                mDbHelper.close();
                shuffle();
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", questions);
                Intent i = new Intent(Main_Activity.this, QuestionActivity.class);
                i.putExtras(bundle);
                startActivityForResult(i, 2);

            }

        });
    }

    private void addTupleFromCsv(FeedReaderDbHelper mDbHelper, InputStream path) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(path));
            String line = " ";
            while ((line = bf.readLine()) != null) {
                line = line.replaceAll("&#039;", "\"").replaceAll("&quot;", "\'").replaceAll("&ldquo;", "\"").replaceAll("&rdquo;", "\"");
                String values[] = line.split("§");
                String toBeInserted[] = Arrays.copyOf(values, values.length + 1);
                toBeInserted[toBeInserted.length - 1] = "1"; //prima dllo shuffle la prima è empre giusta
                mDbHelper.insert(mDbHelper, toBeInserted);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mDbHelper.close();
        }
    }

    private void shuffle() {
        for (String[] line : questions) {
            String[] answers = {line[ANSWER_1], line[ANSWER_2], line[ANSWER_3], line[ANSWER_4]};
            String correctAnswer = line[2];
            Collections.shuffle(Arrays.asList(answers));
            for (int i = 2; i < 6; i++) {
                line[i] = answers[i - 2];
            }
            int correctAnswerIndex = Arrays.asList(line).indexOf(correctAnswer);
            // System.out.println("CORRECT_ ANSWER: "+correctAnswer+" @ " +correctAnswerIndex);
            line[6] = Integer.toString(correctAnswerIndex);
            // System.out.println("ROW: "+Arrays.toString(line));
        }
    }

    private int newRandom() {
        int rand;
        do {
            rand = (int) (Math.random() * NUMBER_OF_QUESTIONS) + 1;
        } while (foundIDs.contains(rand));
        foundIDs.add(rand);
        return rand;
    }
}
