package com.example.error.themoviedb.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.error.themoviedb.R;
import com.squareup.picasso.Picasso;

public class GridCursorAdapter extends CursorAdapter {

    public GridCursorAdapter(Context context,Cursor cursor,int flags) {
        super(context,cursor,flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img = (ImageView) view.findViewById(R.id.poster_image_view);
        Picasso.with(context).load(cursor.getString(4)).into(img);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_layout,parent,false);
        return view;
    }
}
