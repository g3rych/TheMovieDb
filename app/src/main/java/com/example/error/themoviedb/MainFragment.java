package com.example.error.themoviedb;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

public class MainFragment extends Fragment {
    private static String TAG = MainFragment.class.getSimpleName();
    private String movieData;
    private ArrayList<FilmInfo> films = new ArrayList<>();
    private ImageAdapter adapter;

    private GridView gridView;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);


        gridView = (GridView) rootView.findViewById(R.id.grid);
        adapter = new ImageAdapter(getActivity());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CallBack)getActivity()).onItemSelected(films.get(position));
            }
        });

        new DownloadData().execute();
        return rootView;
    }

    public interface CallBack {
        public void onItemSelected(FilmInfo film);
    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.addAll(films);


        }

        @Override
        protected Void doInBackground(Void... params) {
            final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                    "api_key=6dece3ed1b9e1950498be7673d071bdf";
            try {
                getJsonString(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        public void getJsonString(String myurl) throws IOException {
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
            } finally {
                if (bis != null)
                    bis.close();
                movieData = builder.toString();
                parseData(movieData);
            }
        }

        public void parseData(String JSONStr) {
            final String base_url = "http://image.tmdb.org/t/p/";
            final String size = "w185";
            String file_path;


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
                    title = film.getString("original_title");
                    plot = film.getString("overview");
                    rating = film.getDouble("vote_average");
                    file_path = film.getString("poster_path");
                    poster = (base_url + size + file_path);
                    release_date = film.getString("release_date");
                    films.add(new FilmInfo(title,
                            poster,
                            plot,
                            rating,
                            release_date));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
