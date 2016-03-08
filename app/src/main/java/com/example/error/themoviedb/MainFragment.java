package com.example.error.themoviedb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.error.themoviedb.service.ServiceHelper;

import java.util.ArrayList;

public class MainFragment extends Fragment{
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
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    ServiceHelper.getInstance().listViewNextPage(getActivity());
                }
            }
        });
        return rootView;
    }


    public interface CallBack {
        void onItemSelected(FilmItem film);
    }
}
