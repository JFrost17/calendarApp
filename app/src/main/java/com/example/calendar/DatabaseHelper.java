package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.*;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "user_table";
    private static final String COL1 = "username";
    private static final String COL2 = "password";

    private static final String EVENT_TABLE_NAME = "event_table";
    private static final String COL_DATE = "date";
    private static final String COL_NAME = "name";
    private static final String COL_TIME = "time";
    private static final String COL_NOTE = "note";

    private static final String CREATE_TABLE_USER ="CREATE TABLE " + TABLE_NAME + "("+ COL1 + " TEXT," + COL2 +  " TEXT" + ")";
    private static final String CREATE_TABLE_EVENT="CREATE TABLE " + EVENT_TABLE_NAME + "("+ COL1 + " TEXT," + COL_NAME +  " TEXT," + COL_DATE +  " TEXT," + COL_TIME +  " TEXT," + COL_NOTE +  " TEXT" +")";

    public DatabaseHelper(Context context) {
        super(context, TAG, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        onCreate(db);
    }

    public boolean addDataUser(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,username);
        contentValues.put(COL2,password);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result ==-1)
        {
         return  false;
        }
        else
        {
            return true;
        }
    }

    public boolean addDataEvent(String username, String name, String date, String time, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,username);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_TIME, time);
        contentValues.put(COL_NOTE, note);
        long result = db.insert(EVENT_TABLE_NAME,null,contentValues);

        if(result ==-1)
        {
            return  false;
        }
        else
        {
            return true;
        }
    }

    /*public User getUser(String username)
    {
        Log.d("getUser", "This is my message");
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COL1 + " = " + username;
        //Log.e(LOG,selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null) {
            c.moveToFirst();
        }
            User u = new User();
            u.setUsername(c.getString(c.getColumnIndex(COL1)));
            u.setPassword(c.getString(c.getColumnIndex(COL2)));

        return u;
    }*/

    public Cursor getDataUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public void deleteAllUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public Cursor getDataEvent()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + EVENT_TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public void deleteAllEvent()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ EVENT_TABLE_NAME);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
