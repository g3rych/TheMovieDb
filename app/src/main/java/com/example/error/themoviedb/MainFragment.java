package com.example.error.themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.error.themoviedb.service.DownloadService;

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
    private ImageAdapter adapter;
    private ArrayList<FilmInfo> films = new ArrayList<>();
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
                ((CallBack) getActivity()).onItemSelected(films.get(position));
            }
        });
        final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                            "api_key=6dece3ed1b9e1950498be7673d071bdf";
        Intent intent = new Intent(getActivity(), DownloadService.class);
        intent.setData(Uri.parse(URL));
        getActivity().startService(intent);


        IntentFilter filter = new IntentFilter("overview");
        ResponseReciever reciever = new ResponseReciever();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(reciever,filter);
        return rootView;
    }

    public interface CallBack {
        void onItemSelected(FilmInfo film);
    }

    private class ResponseReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            films = (ArrayList<FilmInfo>) intent.getSerializableExtra("films");
            Log.d(TAG,films.size()+"");

            adapter.addAll(films);

        }
    }



}
