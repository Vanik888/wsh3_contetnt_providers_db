package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
    public final static String myLogger = "myLogger";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Uri STUDENTS_URI = Uri
                .parse("content://com.example.app.provider/students");
        Cursor cursor = getContentResolver().query(STUDENTS_URI, null, null,
                null, null);
        startManagingCursor(cursor);
        int nameIndex = cursor.getColumnIndex("name");
        int surnameIndex = cursor.getColumnIndex("surname");
        int ageIndex = cursor.getColumnIndex("age");
        while (cursor.moveToFirst()) {
            Log.d(myLogger, "name = " +  cursor.getString(nameIndex));
        }
        
//        final String CONTACT_NAME = "name";
//        final String CONTACT_EMAIL = "surname";
//        final int STUDENTS_AGE = "age";
//

    }



}




