package com.example.error.themoviedb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;




public class ImageAdapter extends ArrayAdapter<FilmInfo> {
    private static String TAG = ImageAdapter.class.getSimpleName();
    private LayoutInflater mInflater;

    public ImageAdapter(Context context) {
        super(context,R.layout.poster_layout);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.poster_layout,parent,false);
            } else {
                view = convertView;
            }

        FilmInfo item = getItem(position);
        Log.d(TAG,item.getPoster());
        ImageView imgView  = (ImageView) view.findViewById(R.id.poster_image_view);
        Picasso.with(getContext()).load(item.getPoster()).into(imgView);

        return view;

    }
}
