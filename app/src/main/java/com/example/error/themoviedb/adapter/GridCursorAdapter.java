package com.example.error.themoviedb.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public GridCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.ckbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImage();
            }
        });
        holder.textViewFilmTitle.setText(cursor.getString(TITLE));
        Glide.with(context).load(cursor.getString(POSTER))
                .crossFade().placeholder(R.drawable.placeholder)
                .into(holder.imageViewPoster);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    public void clickImage() {
        Toast.makeText(mContext, "some text", Toast.LENGTH_SHORT).show();
    }

    private static class ViewHolder {
        private ImageView imageViewPoster;
        private TextView textViewFilmTitle;
        private CheckBox ckbFavorite;

        public ViewHolder(View view) {
            imageViewPoster = (ImageView) view.findViewById(R.id.poster_image_view);
            textViewFilmTitle = (TextView) view.findViewById(R.id.text_view_film_title);
            ckbFavorite = (CheckBox) view.findViewById(R.id.imgBtn);
        }
    }
}
