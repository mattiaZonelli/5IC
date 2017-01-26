package com.swapnil.thequiz;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Swapnil on 25/01/2017.
 */

public class RankActivity  extends AppCompatActivity {

    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        DatabaseHelper myDbHelper = new DatabaseHelper(RankActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        ArrayList<String> lsView = new ArrayList<String>();
        c = myDbHelper.rankQuery("USERS", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
                String usn = c.getString(1);
                String scr = c.getString(2);
                lsView.add(usn + " --- " + scr);
            } while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_2,
                android.R.id.text1, lsView);

        ListView LV = (ListView)findViewById(R.id.rankList);
        LV.smoothScrollToPosition(0);
        LV.setAdapter(adapter);
    }
}
