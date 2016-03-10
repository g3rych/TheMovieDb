package com.example.error.themoviedb.service;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;


public class ServiceHelper {
    private static ServiceHelper instance = null;
    private int page;

    private ServiceHelper() {
        page = 0;
    }
    public static synchronized ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();

        }
        return instance;
    }

    public void listViewNextPage(Context context) {

        page++;
        Intent intent = new Intent(context, DownloadService.class);
        final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                "api_key=6dece3ed1b9e1950498be7673d071bdf&page=" + page;
        intent.setData(Uri.parse(URL));
        context.startService(intent);

    }
}
