package com.example.error.themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.error.themoviedb.service.DownloadService;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private static String TAG = MainFragment.class.getSimpleName();
    private ImageAdapter adapter;
    private ArrayList<FilmItem> films = new ArrayList<>();
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
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount >= totalItemCount) {
                    final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                            "api_key=6dece3ed1b9e1950498be7673d071bdf&page=2";
                    Intent intent = new Intent(getActivity(), DownloadService.class);
                    intent.setData(Uri.parse(URL));
                    getActivity().startService(intent);

                }

            }
        });

        final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                            "api_key=6dece3ed1b9e1950498be7673d071bdf";
        Intent intent = new Intent(getActivity(), DownloadService.class);
        intent.setData(Uri.parse(URL));
        getActivity().startService(intent);

        return rootView;
    }

    public interface CallBack {
        void onItemSelected(FilmItem film);
    }
}
