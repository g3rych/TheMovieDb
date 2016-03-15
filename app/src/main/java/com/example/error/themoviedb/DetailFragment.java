package com.example.error.themoviedb;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.error.themoviedb.adapter.DetailCursorAdapter;
import com.example.error.themoviedb.adapter.GridCursorAdapter;
import com.example.error.themoviedb.adapter.RecycleViewCursorAdapter;


public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String TAG = DetailFragment.class.getSimpleName();
    Uri contentUri;
    int movie_id;


    ImageView posterImgView;
    TextView textViewTitle;
    TextView textViewPlot;
    //ListView listViewTrailers;
    RecyclerView recyclerView;


    DetailCursorAdapter adapter;

    RecycleViewCursorAdapter adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        posterImgView = (ImageView) rootView.findViewById(R.id.img_poster_view);
        textViewTitle = (TextView) rootView.findViewById(R.id.textView_title);
        textViewPlot = (TextView) rootView.findViewById(R.id.textView_plot);
        //listViewTrailers = (ListView) rootView.findViewById(R.id.list_view_trailers);


        movie_id = getArguments().getInt("movie_id");
        contentUri = getArguments().getParcelable("uri");

        //adapter = new DetailCursorAdapter(getActivity(),null,0);
        //listViewTrailers.setAdapter(adapter);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_list_trailers);
        adapter2 = new RecycleViewCursorAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter2);

        getLoaderManager().initLoader(0,null,this);
        return rootView;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter2.swapCursor(data);
        if (data != null && data.moveToFirst()) {
            textViewTitle.setText(data.getString(GridCursorAdapter.TITLE));
            textViewPlot.setText(data.getString(GridCursorAdapter.PLOT));
            Glide.with(this).load(data.getString(GridCursorAdapter.POSTER)).crossFade().into(posterImgView);
            if (data.getString(9) != null) {

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
