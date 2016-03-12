package com.example.error.themoviedb.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.error.themoviedb.data.MoviesProvider;



public class DownloadService extends IntentService {
    public static String ACTION_GRID_OVERVIEW = "ACTION_GRID_OVERVIEW";
    public static String ACTION_TRAILERS_LIST = "ACTION_TRAILERS_LIST";
    public static String ACTION_CLEAR_DB = "ACTION_CLEAR_DB";
    private static String TAG = DownloadService.class.getSimpleName();

    public DownloadService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String URL;
        if (intent.getStringExtra("action").equals(ACTION_GRID_OVERVIEW)) {
            URL = intent.getDataString();
            new GridOverViewCommand().execute(URL, getApplicationContext());
        } else if (intent.getStringExtra("action").equals(ACTION_TRAILERS_LIST)) {
            URL = intent.getDataString();
            new TrailersCommand().execute(URL,getApplicationContext());
        } else if (intent.getStringExtra("action").equals(ACTION_CLEAR_DB)) {
            clearDB();
        }
    }

    public void clearDB() {
        getContentResolver().delete(MoviesProvider.MOVIE_PATH, null, null);
    }
}

