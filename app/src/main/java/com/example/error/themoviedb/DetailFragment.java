package com.example.error.themoviedb;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.error.themoviedb.adapter.GridCursorAdapter;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String TAG = DetailFragment.class.getSimpleName();
    Uri contentUri;


    ImageView posterImgView;
    TextView textViewTitle;
    TextView textViewPlot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        posterImgView = (ImageView) rootView.findViewById(R.id.img_poster_view);
        textViewTitle = (TextView) rootView.findViewById(R.id.textView_title);
        textViewPlot = (TextView) rootView.findViewById(R.id.textView_plot);


        contentUri = getArguments().getParcelable("uri");

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
            Glide.with(this).load(data.getString(GridCursorAdapter.POSTER)).crossFade().into(posterImgView);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new CursorLoader(getActivity(),contentUri,null,null,null,null);
    }
}
