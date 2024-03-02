package com.example.myecomerceapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "TODO.DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "TASK";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String REMARKS = "remarks";
    public static final String STATUS = "status";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"(" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+ TITLE + " TEXT NOT NULL," + DESCRIPTION + " TEXT," + REMARKS + " TEXT," + STATUS + " TEXT NOT NULL);";
    public SQLiteDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
