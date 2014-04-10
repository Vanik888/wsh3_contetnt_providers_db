package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vanik on 31.03.14.
 */
class DBHelper extends SQLiteOpenHelper {
    private final static String myLogger = "myLogger";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(myLogger, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table groups ("
                + "_id integer primary key autoincrement,"
                + "group_number text,"
                + "curse text" + ");");
        db.execSQL("create table students ("
                + "_id integer primary key autoincrement,"
                + "name text,"
                + "surname text,"
                + "age int"+ ");");
        ContentValues cvGroups = new ContentValues();
        cvGroups.put("group_number", "1 ");
        cvGroups.put("curse", "curse");
        Long aLong = db.insert("groups",null, cvGroups);
        ContentValues cvStudents = new ContentValues();
        cvStudents.put("name", "misha ");
        cvStudents.put("surname", "vasiliev");
        cvStudents.put("age", 11);
        Long insert = db.insert("students",null, cvStudents);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
