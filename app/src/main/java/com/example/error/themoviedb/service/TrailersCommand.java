package com.example.error.themoviedb.service;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.error.themoviedb.data.DBContract;
import com.example.error.themoviedb.data.MoviesProvider;

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

public class TrailersCommand {

    public void execute(String url, Context context) {
        getTrailers(getJsonString(url), context);
    }

    private String getJsonString(String myurl) {
        BufferedInputStream bis = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int responseCode = conn.getResponseCode();
            bis = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            conn.disconnect();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public void getTrailers(String JSONString, Context context) {
        int id;
        String source;
        String name;
        Log.d("TrailersCommand",JSONString);
        try {
            JSONObject detailStr = new JSONObject(JSONString);
            id = detailStr.getInt("id");
            JSONObject trailers = detailStr.getJSONObject("trailers");
            JSONArray youtube = trailers.getJSONArray("youtube");

            for (int i = 0; i < youtube.length(); i++) {
                source = youtube.getJSONObject(i).getString("source");
                name = youtube.getJSONObject(i).getString("name");
                ContentValues cv = new ContentValues();
                cv.put(DBContract.TrailersEntry.MOVIE_id, id);
                cv.put(DBContract.TrailersEntry.NAME,name);
                cv.put(DBContract.TrailersEntry.SOURCE, source);
                Log.d("TrailersCommand", source);
                context.getContentResolver().insert(MoviesProvider.TRAILER_PATH, cv);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
