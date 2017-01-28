package com.gallez.pool.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pool on 26/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Quiz.sqlite";
    public static final String _ID = BaseColumns._ID;
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Questions";

    public static final String[] COLUMNS = {"Question","Answer1","Answer2","Answer3","Answer4","Correct"};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+TABLE+"("+
                    _ID+" INTEGER PRIMARY KEY," +
                    "Question varchar(100),"+
                    "Answer1 varchar(100)," +
                    "Answer2 varchar(100)," +
                    "Answer3 varchar(100)," +
                    "Answer4 varchar(100)," +
                    "Correct integer" +
                    ");";

    public static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXISTS "+TABLE;

    public String getColumn(int index){
        return COLUMNS[index];
    }

    public int columnsCount(){
        return COLUMNS.length;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static boolean isTableEmpty(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select count(*) from "+TABLE,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count == 0;
    }

    public static ArrayList<Question> getQuestionsAndAnswers(SQLiteDatabase db){
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE,                     // The table to query
                COLUMNS,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        while(cursor.moveToNext()){
            Question q = new Question();
            for(int i = 0; i<COLUMNS.length; i++){
                String s = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNS[i]));
                switch (i){
                    case 0: q.setQuestion(s);
                        break;
                    case 5: q.setCorrect(s);
                        break;
                    default:q.addAnswer(s,i-1);
                }
            }
            questions.add(q);
        }
        return questions;
    }

    public static void populateDatabase(SQLiteDatabase db,ArrayList<Question> questions){
        for(Question q: questions){
            ContentValues cv = new ContentValues();
            cv.put(COLUMNS[0],q.getQuestion());
            String [] answers = q.getAnswers();
            for(int i = 1; i<=answers.length; i++)
                cv.put(COLUMNS[i],answers[i-1]);
            cv.put(COLUMNS[COLUMNS.length-1],q.getCorrect());
            long id = db.insert(TABLE,null,cv);
        }
    }



}
