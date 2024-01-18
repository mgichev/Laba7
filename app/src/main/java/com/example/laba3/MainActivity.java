package com.example.laba3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<User> users = new ArrayList<User>();

    EditText emailText, passwordText;
    Button loginButton;

    String[] emails, passwords;

    boolean isPasswordCorrect = false;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    private void openDatabase(@NonNull Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    private void closeDatabaseObjects() {
        database.close();
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.horizontal_activity_main);
        }
        else {
            setContentView(R.layout.activity_main);
        }
        openDatabase(getBaseContext());
        SharedPreferences sp = getSharedPreferences("SharedPref", MODE_PRIVATE);
        boolean isAuthLoaded = sp.getBoolean("AuthDBLoaded", false);
        if (!isAuthLoaded) {
            Resources res = getResources();
            passwords = res.getStringArray(R.array.passwords);
            emails = res.getStringArray(R.array.emails);
            initDB();
            sp.edit().putBoolean("AuthDBLoaded", true).apply();
        }
        loadData();
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emailText = findViewById(R.id.emailText);
                passwordText = findViewById(R.id.passwordText);
                isPasswordCorrect = CheckIsPasswordCorrect(users, emailText.getText().toString(), passwordText.getText().toString());
                if (isPasswordCorrect) {
                    emailText.setTextColor(Color.BLACK);
                    passwordText.setTextColor(Color.BLACK);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
                else {
                    emailText.setTextColor(Color.RED);
                    passwordText.setTextColor(Color.RED);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDatabaseObjects();
    }

    private void loadData() {
        cursor = database.rawQuery("SELECT * from " + DatabaseHelper.TABLE_USERS, null);
        while (cursor.moveToNext()) {
            int id = 0;
            String email, password;
            id = cursor.getInt(0);
            email = cursor.getString(1);
            password = cursor.getString(2);
            User user = new User(email, password);
            users.add(user);
        }
    }

    private void initDB() {
        Log.d("init", "initDB");
        for (int i = 0; i < emails.length; i++) {
            ContentValues cv = new ContentValues();
            String email = emails[i], password = passwords[i];
            cv.put(DatabaseHelper.COLUMN_EMAIL, email);
            cv.put(DatabaseHelper.COLUMN_PASSWORD, password);
            database.insert(DatabaseHelper.TABLE_USERS, null, cv);
        }
    }

    private static boolean CheckIsPasswordCorrect(ArrayList<User> users,
                                                  String inputEmail, String inputPassword) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equals(inputEmail)) {
                if (user.getPassword().equals(inputPassword)) {
                    User.setCurrentUserID(i);
                    return true;
                };
            }
        }
        return false;
    }
}