package com.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by rainstorm on 1/24/17.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "QuizDatabase.sqlite";
    private static final String[] NAME_FIELDS = {
            BaseColumns._ID, "QUESTION", "ANSWER_1", "ANSWER_2", "ANSWER_3", "ANSWER_4", "CORRECT_ANSWER"
    };
    private static final String[] FIELDS = {
            "_id INTEGER PRIMARY KEY AUTOINCREMENT,", "QUESTION TEXT,", "ANSWER_1 TEXT,", "ANSWER_2 TEXT,", "ANSWER_3 TEXT,", "ANSWER_4 TEXT,", "CORRECT_ANSWER INTEGER"
    };
    private final String TABLE_NAME;

    public FeedReaderDbHelper(Context context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        TABLE_NAME = tableName;
        this.getWritableDatabase();
    }

    public static int numberOfColumns() {
        return NAME_FIELDS.length;
    }

    public static String getField(int index) {
        return NAME_FIELDS[index];
    }

    public void onDestroy(SQLiteDatabase db) {
        Log.v("DESTROY", "DROP TABLE");
        db.execSQL("DROP TABLE" + TABLE_NAME);
    }

    public boolean isEmpty() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null).getCount() == 0;
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( ";
        for (String s : FIELDS) {
            query += s;
        }
        query += ");";

        System.out.println(query);
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(String[] values) {
        Log.v(Integer.toString(Log.ASSERT), "INSERT IN DB");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.v("LENGTH", Integer.toString(values.length));
        for (int i = 1; i < NAME_FIELDS.length; i++) {
            contentValues.put(NAME_FIELDS[i], values[i - 1]);
        }
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor read(int index) {

        SQLiteDatabase db = getReadableDatabase();


        // Define a projection that specifies which columns from the database
// you will actually use after this query.


        // Filter results WHERE "title" = 'My Title'
        String[] selectionArgs = {Integer.toString(index)};
        // How you want the results sorted in the resulting Cursor
        //String sortOrder ="ASC";
        //  FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " ASC";

        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                NAME_FIELDS,                               // The columns to return
                NAME_FIELDS[0] + "= ?",                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null// sortOrder                                 // The sort order
        );
        return cursor;
    }

}