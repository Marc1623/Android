package com.example.projet_laurin_marc.static_database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    public static final String DATABASE_NAME = "CountiesDB";
    public final static String DATABASE_PATH = "/data/data/com.example.projet_laurin_marc/databases/";
    public static final int DATABASE_VERSION = 2;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    //Create a empty database on the system
    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if (!dbExist1) {
            this.getReadableDatabase();
            this.close();
            copyDataBase();
        }

    }

    //Check database already exist or not
    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        }
        return checkDB;
    }

    //Copies database from local assets-folder to the just created empty database in the system folder
    public boolean copyDataBase() {
        try {

            InputStream mInput = myContext.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[2024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //delete database
    public void db_delete() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    //Open database
    public void openDatabase() throws SQLException {
        String dbPath = myContext.getDatabasePath(DATABASE_NAME).getPath();
        if (myDataBase != null && myDataBase.isOpen()) {
            return;
        }
        myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase() throws SQLException {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }
    }

    public List<County1> getCountiesByCanton(String selectedCanton) {
        County1 county = null;
        List<County1> countyList = new ArrayList<>();
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Counties_De Where Kanton like '" + selectedCanton + "'   Order By Gemeinde " , null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            county = new County1(cursor.getString(0), cursor.getString(1));
            countyList.add(county);
            cursor.moveToNext();
        }

        cursor.close();
        closeDataBase();
        return countyList;
    }

}
