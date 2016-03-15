package com.example.error.themoviedb.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.error.themoviedb.R;

public class DetailCursorAdapter extends CursorAdapter {

    public DetailCursorAdapter(Context context,Cursor cursor,int flags) {
        super(context,cursor,flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.new_trailer_layout,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.text_view_trailer);
        textView.setText(cursor.getString(9));
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_trailer);
        String url = "http://img.youtube.com/vi/" +
                cursor.getString(10) +
                "/hqdefault.jpg";
        Glide.with(context).load(url).crossFade().into(imageView);
    }
}
