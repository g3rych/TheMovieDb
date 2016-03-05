package com.example.error.themoviedb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.error.themoviedb.data.DBContract.MoviesEntry;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "themovie.db";
    public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTable =
                "CREATE TABLE " + MoviesEntry.TABLE_NAME + " ( " +
                        MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        MoviesEntry.MOVIE_id + " INTEGER NOT NULL, " +
                        MoviesEntry.TITLE + " TEXT NOT NULL, " +
                        MoviesEntry.PLOT + " TEXT NOT NULL, " +
                        MoviesEntry.POSTER + " TEXT NOT NULL, " +
                        MoviesEntry.RATING + " REAL NOT NULL, " +
                        MoviesEntry.RELEASE_DATE + " TEXT NOT NULL " + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
