package com.example.error.themoviedb.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.error.themoviedb.FilmInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadService extends IntentService {
    public static String FILM_ID = "FILM_ID";

    private static String TAG = DownloadService.class.getSimpleName();

    public DownloadService(){
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String movieData = "";
        final String URL = intent.getDataString();

        try {
            Log.d(TAG,URL);
            movieData = getJsonString(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (intent.getBooleanExtra("isDetails",false) == false) {
             parseData(movieData);
        } else {
            getTrailers(movieData);
        }

    }

    public String getJsonString(String myurl) throws IOException {
        BufferedInputStream bis = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d(TAG, "HTTP response code : " + responseCode);
            bis = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
            if (bis != null)
                bis.close();
           return builder.toString();
    }


    public void parseData(String JSONStr) {
        ArrayList<FilmInfo> films = new ArrayList<>();
        Log.d(TAG,"parseDATA");
        final String base_url = "http://image.tmdb.org/t/p/";
        final String size = "w185";
        String file_path;
        int id;
        String title;
        String plot;
        String poster;
        String release_date;
        double rating;
        try {
            JSONObject movieStr = new JSONObject(JSONStr);
            JSONArray results = movieStr.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject film = results.getJSONObject(i);
                id = film.getInt("id");
                title = film.getString("original_title");
                plot = film.getString("overview");
                rating = film.getDouble("vote_average");
                file_path = film.getString("poster_path");
                poster = (base_url + size + file_path);
                release_date = film.getString("release_date");
                films.add(new FilmInfo(id,
                        title,
                        poster,
                        plot,
                        rating,
                        release_date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Intent localIntent = new Intent("overview");
            localIntent.putExtra("films", films);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
    }
    public void getTrailers(String JSONString) {
        ArrayList<String> trailerList = new ArrayList<String>();
        try {
            JSONObject detailStr = new JSONObject(JSONString);
            JSONObject trailers = detailStr.getJSONObject("trailers");
            JSONArray youtube = trailers.getJSONArray("youtube");
            Log.d(TAG,""+youtube.length());
            for (int i = 0; i < youtube.length(); i++) {
                String source = youtube.getJSONObject(i).getString("source");
                Log.d(TAG,source);
                trailerList.add(source);
            }
            Intent trailersIntent = new Intent("trailers");
            trailersIntent.putExtra("trailers",trailerList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(trailersIntent);

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

