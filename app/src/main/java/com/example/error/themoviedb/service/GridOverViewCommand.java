package com.example.error.themoviedb.service;

import android.content.ContentValues;
import android.content.Context;

import com.example.error.themoviedb.FilmItem;
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

public class GridOverViewCommand {
    public void execute(String url, Context context) {
        parseData(getJsonString(url), context);
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

    public void parseData(String JSONStr, Context context) {
        ArrayList<FilmItem> films = new ArrayList<>();
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
                films.add(new FilmItem(id,
                        title,
                        poster,
                        plot,
                        rating,
                        release_date));
                ContentValues cv = new ContentValues();
                cv.put(DBContract.MoviesEntry.MOVIE_id, id);
                cv.put(DBContract.MoviesEntry.TITLE, title);
                cv.put(DBContract.MoviesEntry.POSTER, poster);
                cv.put(DBContract.MoviesEntry.PLOT, plot);
                cv.put(DBContract.MoviesEntry.RATING, rating);
                cv.put(DBContract.MoviesEntry.RELEASE_DATE, release_date);
                context.getContentResolver().insert(MoviesProvider.MOVIE_PATH, cv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
