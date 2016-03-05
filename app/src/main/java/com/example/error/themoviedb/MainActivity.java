package com.example.error.themoviedb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.error.themoviedb.data.DBHelper;


public class MainActivity extends AppCompatActivity implements
                                    MainFragment.CallBack {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

    }

    @Override
    public void onItemSelected(FilmInfo film) {
        Intent intent  = new Intent(this, DetailActivity.class);
        intent.putExtra("FILM",film);

        startActivity(intent);

    }
}

