package com.example.error.themoviedb.service;

import android.content.ContentValues;
import android.content.Context;

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
        ArrayList<String> trailerList = new ArrayList<String>();
        int id;
        String source;
        try {
            JSONObject detailStr = new JSONObject(JSONString);
            id = detailStr.getInt("id");
            JSONObject trailers = detailStr.getJSONObject("trailers");
            JSONArray youtube = trailers.getJSONArray("youtube");

            for (int i = 0; i < youtube.length(); i++) {
                source = youtube.getJSONObject(i).getString("source");

                trailerList.add(source);
                ContentValues cv = new ContentValues();
                cv.put(DBContract.TrailersEntry.MOVIE_id, id);
                cv.put(DBContract.TrailersEntry.SOURCE, source);
                context.getContentResolver().insert(MoviesProvider.TRAILER_PATH, cv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
