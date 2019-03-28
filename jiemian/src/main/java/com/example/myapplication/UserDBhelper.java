package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBhelper extends SQLiteOpenHelper {
    //private static final int VERSION = 1;
    public UserDBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Clover", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user" +
                "(userID text," +
                "temperature text," +
                "weight text," +
                "heartbeat text," +
                "systolicPressure text," +
                "diastolicPressure text," +
                "bloodFat text," +
                "dateTime TimeStamp DEFAULT(datetime('now', 'localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table user");
        db.execSQL("create table user" +
                "(userID text," +
                "temperature text," +
                "weight text," +
                "heartbeat text," +
                "systolicPressure text," +
                "diastolicPressure text," +
                "bloodFat text," +
                "dateTime TimeStamp DEFAULT(datetime('now', 'localtime')))");
    }
}
