package com.example.myecomerceapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DatabaseManager {

    private Context context;
    private SQLiteDbHelper sqLiteDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public DatabaseManager open() throws SQLException {
    sqLiteDbHelper=new SQLiteDbHelper(context);
    sqLiteDatabase=sqLiteDbHelper.getWritableDatabase();
    return this;
    }

    public void close(){
        sqLiteDbHelper.close();

    }
}
