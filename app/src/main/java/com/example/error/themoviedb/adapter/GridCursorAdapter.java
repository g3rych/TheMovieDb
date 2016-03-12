package com.example.error.themoviedb.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.error.themoviedb.R;


public class GridCursorAdapter extends CursorAdapter {
    public static final int _ID = 0;
    public static final int MOVIE_ID = 1;
    public static final int TITLE = 2;
    public static final int PLOT = 3;
    public static final int POSTER = 4;
    public static final int RATING = 5;
    public static final int RELEASE_DATE = 6;

    public GridCursorAdapter(Context context,Cursor cursor,int flags) {
        super(context,cursor,flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img = (ImageView) view.findViewById(R.id.poster_image_view);

        Glide.with(context).load(cursor.getString(POSTER)).crossFade().into(img);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_layout,parent,false);
        return view;
    }
}
