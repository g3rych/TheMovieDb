package com.example.error.themoviedb;



import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.error.themoviedb.adapter.GridCursorAdapter;
import com.example.error.themoviedb.data.MoviesProvider;
import com.example.error.themoviedb.service.ServiceHelper;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String TAG = MainFragment.class.getSimpleName();
    private GridCursorAdapter adapter;
    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        gridView = (GridView) rootView.findViewById(R.id.grid);
        adapter = new GridCursorAdapter(getActivity(),null,0);
        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                long _id = cursor.getLong(0);
                int movie_id = cursor.getInt(1);
                Uri uri = MoviesProvider.MOVIE_PATH.buildUpon().appendPath(Long.toString(_id)).build();
                ((CallBack) getActivity()).onItemSelected(uri, movie_id);
            }
        });
        gridView.setOnScrollListener(new EndLessScroll(getActivity()));


        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MoviesProvider.MOVIE_PATH,null,null,null,null);
    }

    public interface CallBack {
        void onItemSelected(Uri uri,int movie_id);
    }

}
