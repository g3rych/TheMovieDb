package com.example.error.themoviedb.service;


import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.error.themoviedb.Utily;


public class ServiceHelper {
    private static ServiceHelper instance = null;
    private int page;
    private boolean cleaned = false;

    private ServiceHelper() {
        page = 0;
    }

    public static synchronized ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();

        }
        return instance;
    }

    public void gridViewNextPage(Context context) {
        page++;
        Intent intent = new Intent(context, DownloadService.class);
        final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                "api_key=6dece3ed1b9e1950498be7673d071bdf&page=" + page;
        intent.setData(Uri.parse(URL));
        intent.putExtra("action", DownloadService.ACTION_GRID_OVERVIEW);
        context.startService(intent);
        Log.d("ServiceHelper",URL);
    }
    public void getTrailers(Context context,int movie_id) {
        Intent intent = new Intent(context, DownloadService.class);
        final String URL = "http://api.themoviedb.org/3/movie/" + movie_id +
                "?api_key=6dece3ed1b9e1950498be7673d071bdf&append_to_response=trailers";
        intent.setData(Uri.parse(URL));
        intent.putExtra("action", DownloadService.ACTION_TRAILERS_LIST);
        context.startService(intent);
        Log.d("TrailerServiceHelper",URL);
    }

    public void clearDB(Context context) {
        if (!cleaned && Utily.isConnected(context)) {
            Intent intent = new Intent(context, DownloadService.class);
            intent.putExtra("action", DownloadService.ACTION_CLEAR_DB);
            context.startService(intent);
            cleaned = true;
            Log.d("ServiceHelper",""+cleaned);
        }
    }
}
