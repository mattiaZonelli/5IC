package com.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main_Activity extends Activity {

    private final int ANSWER_1 = 2;
    private final int ANSWER_2 = 3;
    private final int ANSWER_3 = 4;
    private final int ANSWER_4 = 5;
    private final String EASY = "Easy";
    private final String MEDIUM = "Medium";
    private final String MULTIPLE_CHOICE = "QuestionsAndAnswers";
    public String questions[][];
    private int NUMBER_OF_QUESTIONS = 12;
    public ArrayList<Integer> foundIDs = new ArrayList<>(NUMBER_OF_QUESTIONS);
    private String CSV_FILE_NAME = "file.csv";
    private int CODE = 3;
    private String chosenQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button quiz1 = (Button) (findViewById(R.id.newActivityButton));
        Button quiz2 = (Button) (findViewById(R.id.newActivity2));
        Button quit = (Button) (findViewById(R.id.quitButton));

        final Spinner spinner = (Spinner) (findViewById(R.id.spinner));

        ArrayList<String> values = new ArrayList<>();
        values.add("Easy");
        values.add("Medium");
        values.add("Difficult");

        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, values));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(((String) (spinner.getSelectedItem())));
                if (spinner.getSelectedItem().equals(EASY)) {
                    NUMBER_OF_QUESTIONS = 10;
                } else if (spinner.getSelectedItem().equals(MEDIUM)) {
                    NUMBER_OF_QUESTIONS = 20;
                } else {
                    NUMBER_OF_QUESTIONS = 30;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        quiz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("QUIZ 1");
                chosenQuiz = "QUIZ 1 RESULTS: ";
                handle();
            }

        });

        quiz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("QUIZ 2");
                chosenQuiz = "QUIZ 2 RESULTS: ";
                handle();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            if (resultCode == CODE) {
                int score = data.getIntExtra("SCORE", -1);
                if (score > -1) {
                    String result = chosenQuiz + "\nYour score is " + score + "/" + NUMBER_OF_QUESTIONS + " (" + (score / NUMBER_OF_QUESTIONS) + "% )\n";
                    if (score <= NUMBER_OF_QUESTIONS / 2) {
                        result += "What a low score! You should be more curious";
                    } else if (score >= NUMBER_OF_QUESTIONS * 0.75) {
                        result += "Man you're an ace!";
                    } else {
                        result += "You should struggle a little more";
                    }
                    ((TextView) findViewById(R.id.score)).setText(result);
                }
            }
        }

    }

    private void handle() {
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(Main_Activity.this.getApplicationContext(), MULTIPLE_CHOICE);
        questions = new String[NUMBER_OF_QUESTIONS][FeedReaderDbHelper.numberOfColumns()];
        foundIDs.clear();
        if (mDbHelper.isEmpty()) { //database does not exists
            try {
                addTupleFromCsv(mDbHelper, getAssets().open(CSV_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < questions.length; i++) {
            Cursor cursor = mDbHelper.read(newRandom());
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
        startActivityForResult(i, CODE);
    }

    private void addTupleFromCsv(FeedReaderDbHelper mDbHelper, InputStream path) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(path));
            String line = " ";
            while ((line = bf.readLine()) != null) {
                line = line.replaceAll("&#039;", "\"").replaceAll("&quot;", "\'").replaceAll("&ldquo;", "\'").replaceAll("&rdquo;", "\'").replaceAll("&amp;", "&");
                String values[] = line.split("§");
                String toBeInserted[] = Arrays.copyOf(values, values.length + 1);
                toBeInserted[toBeInserted.length - 1] = "1"; //prima dllo shuffle la prima è empre giusta
                mDbHelper.insert(toBeInserted);
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
            rand = (new Random(System.currentTimeMillis())).nextInt(Integer.SIZE - 1) + 1;
        } while (foundIDs.contains(rand));
        foundIDs.add(rand);
        return rand;
    }


}
