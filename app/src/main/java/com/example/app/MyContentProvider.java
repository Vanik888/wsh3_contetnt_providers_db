package com.example.app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by vanik on 31.03.14.
 */
public class MyContentProvider extends ContentProvider {
    private static String myLogger = "myLogger";

    static final String DB_NAME = "mydb";
    static final int DB_VERSION = 1;

    //TABLE NAME
    static final String GROUPS_TABLE = "groups";
    static final String STUDENTS_TABLE = "students";


    static final String GROUPS_ID = "_id";
    static final String GROUPS_NAME = "group_number";
    static final String GROUPS_CURSE = "curse";


    static final String STUDENTS_ID = "_id";
    static final String STUDENTS_NAME = "name";
    static final String STUDENTS_SURNAME = "surname";
    static final String STUDENTS_AGE = "age";


    // Скрипт создания таблицы
    static final String DB_CREATE_GROUPS = "create table " + GROUPS_TABLE+ "("
            + GROUPS_ID+ " integer primary key autoincrement, "
            + GROUPS_NAME + " text, " + GROUPS_CURSE+ " text" + ");";

    // Скрипт создания таблицы
    static final String DB_CREATE_STUDENTS = "create table " + STUDENTS_TABLE+ "("
            + STUDENTS_ID + " integer primary key autoincrement, "
            + STUDENTS_NAME+ " text, " + STUDENTS_SURNAME+ " text, " + STUDENTS_AGE + "int"+ ");";




    static final String AUTHORITY = "com.example.app.provider";

    static final String GROUPS_PATH = "groups";
    static final String STUDENTS_PATH = "students";


    public static final Uri GROUPS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + GROUPS_PATH);

    public static final Uri STUDENTS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + STUDENTS_PATH);


    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);



//    // Типы данных
//    // набор строк
//    static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
//            + AUTHORITY + "." + CONTACT_PATH;
//
//    // одна строка
//    static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
//            + AUTHORITY + "." + CONTACT_PATH;


    DBHelper dbHelper;
    SQLiteDatabase db;
    private final static int groupsUriID = 1;
    private final static int studentsUriID = 2;
    @Override
    public boolean onCreate() {
        Log.d(myLogger, "onCreateContentProvider");

        sUriMatcher.addURI(AUTHORITY, GROUPS_PATH, groupsUriID);
        sUriMatcher.addURI(AUTHORITY, STUDENTS_PATH, studentsUriID);

        dbHelper = new DBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
            Log.d(myLogger, "contentProviderQuery, " + uri.toString());
            switch (sUriMatcher.match(uri)) {
                case studentsUriID:
                    Log.d(myLogger, "URI_GROUPS");
                    if (TextUtils.isEmpty(sortOrder)) {
                        sortOrder = STUDENTS_NAME + " ASC";
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Wrong URI: " + uri);
            }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(STUDENTS_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
               STUDENTS_CONTENT_URI);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(myLogger, "insert");
        if (sUriMatcher.match(uri) != studentsUriID)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        if (sUriMatcher.match(uri) != groupsUriID)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        String table_name = uri.getPath();
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(table_name, null, contentValues);
        Uri resultUri = ContentUris.withAppendedId(uri, rowID);

        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
