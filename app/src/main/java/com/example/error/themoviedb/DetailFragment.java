package com.example.error.themoviedb;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.error.themoviedb.adapter.GridCursorAdapter;
import com.example.error.themoviedb.service.ServiceHelper;

import java.util.ArrayList;
import java.util.Arrays;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String TAG = DetailFragment.class.getSimpleName();
    Uri contentUri;
    int movie_id;


    ImageView posterImgView;
    TextView textViewTitle;
    TextView textViewPlot;
    ListView listViewTrailers;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        posterImgView = (ImageView) rootView.findViewById(R.id.img_poster_view);
        textViewTitle = (TextView) rootView.findViewById(R.id.textView_title);
        textViewPlot = (TextView) rootView.findViewById(R.id.textView_plot);
        listViewTrailers = (ListView) rootView.findViewById(R.id.list_view_trailers);
        movie_id = getArguments().getInt("movie_id");
        contentUri = getArguments().getParcelable("uri");
        adapter = new ArrayAdapter<>(getActivity(),R.layout.trailer_layout,R.id.text_view_trailer);
        listViewTrailers.setAdapter(adapter);
        getLoaderManager().initLoader(0,null,this);
        return rootView;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            textViewTitle.setText(data.getString(GridCursorAdapter.TITLE));
            textViewPlot.setText(data.getString(GridCursorAdapter.PLOT));
            Log.d("detailFragment", Arrays.toString(data.getColumnNames()));
            Glide.with(this).load(data.getString(GridCursorAdapter.POSTER)).crossFade().into(posterImgView);
            if (data.getString(9) != null) {
                adapter.add(data.getString(9));
            } else {
                Log.d("detailFragment","data is null");
            }
        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("detailFragment",contentUri.toString());
        return  new CursorLoader(getActivity(),contentUri,null,null,null,null);
    }
}
