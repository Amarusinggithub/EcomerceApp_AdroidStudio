package com.example.myecomerceapp.activitys;

public class AppConstants {
    public static final String DB_NAME = "Ecomerce.DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Users";
    public static final String _ID = "_id";
    public static final String USERNAME = "Username";
    public static final String Email = "Email";
    public static final String PASSWORD = "Password";
    public static final String STATUS = "status";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"(" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+ USERNAME + " TEXT NOT NULL," + Email + " TEXT," + PASSWORD + " TEXT," + STATUS + " TEXT NOT NULL);";
}
