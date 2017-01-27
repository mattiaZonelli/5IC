package fabiomanfrin.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Fabio on 25/01/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance;
    private static final String DB_NAME = "Quiz.sqlite";

    private SQLiteDatabase myDatabase;
    private Context myContext;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) { sInstance = new DBHelper(context.getApplicationContext()); }
        return sInstance;
    }


    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try {
            String myPath = myContext.getDatabasePath(DB_NAME).toString();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){}

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(!dbExist) {

            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying myDatabase");
            }
        }

    }


    private void copyDataBase() throws IOException{

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = myContext.getDatabasePath(DB_NAME).toString();
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() {

        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String myDbPath = myContext.getDatabasePath(DB_NAME).toString();
        myDatabase = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY);
    }


    @Override
    public synchronized void close() {
        if (myDatabase != null) { myDatabase.close(); }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
