package com.example.laba3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "Cities";
    protected static final int SCH_VERSION = 1;

    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_CITIES = "Cities";
    public static final String COLUMN_CITIES_ID = "id";
    public static final String COLUMN_CAPITAL = "capital";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_POPULATION = "population";
    public static final String COLUMN_SQUARE = "square";
    public static final String COLUMN_LANGUAGE = "language";

    public static final String TABLE_SAVED_CITIES = "Saved_Cities";
    public static final String COLUMN_SAVED_CITIES_ID = "id";
    public static final String COLUMN_SAVED_CITY_NAME = "capital";
    public static final String COLUMN_CURRENT_USER_ID = "user_id";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCH_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_USERS
                        + "("
                        + COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
                        + COLUMN_PASSWORD + " TEXT"
                        + ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_CITIES
                        + "("
                        + COLUMN_CITIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_CAPITAL + " TEXT, "
                        + COLUMN_COUNTRY + " TEXT, "
                        + COLUMN_POPULATION + " INTEGER, "
                        + COLUMN_SQUARE + " INTEGER, "
                        + COLUMN_LANGUAGE + " TEXT"
                        + ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_SAVED_CITIES
                        + "("
                        + COLUMN_SAVED_CITIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_SAVED_CITY_NAME + " TEXT, "
                        + COLUMN_CURRENT_USER_ID + " INTEGER DEFAULT 0 "
                        + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
