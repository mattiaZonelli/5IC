package mariano.mazzucchi.quiz;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.esercizi.quiztpsit/databases/";

    private static String DB_NAME = "DbQuiz.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
        } else {
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
}
public SQLiteDatabase getDataBase() {
    return myDataBase;
}
private boolean checkDataBase(){
    SQLiteDatabase checkDB = null;
    try{
        String myPath = myContext.getDatabasePath(DB_NAME).toString();
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }catch(SQLiteException e){
    }
    if(checkDB != null){
        checkDB.close();
    }
    return checkDB != null ? true : false;
}
private void copyDataBase() throws IOException {
    InputStream myInput = myContext.getAssets().open(DB_NAME);
    String outFileName = myContext.getDatabasePath(DB_NAME).toString();
    OutputStream myOutput = new FileOutputStream(outFileName);
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
        myOutput.write(buffer, 0, length);
    }
    myOutput.flush();
    myOutput.close();
    myInput.close();
}
public void openDataBase() throws SQLException {
    String myPath = myContext.getDatabasePath(DB_NAME).toString();
    myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
}
@Override
public synchronized void close() {
    if(myDataBase != null)
        myDataBase.close();
    super.close();
}
@Override
public void onCreate(SQLiteDatabase db) {
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
}
}
