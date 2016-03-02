package com.example.error.themoviedb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView img_poster = (ImageView) rootView.findViewById(R.id.img_poster_view);
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_plot = (TextView) rootView.findViewById(R.id.tv_plot);


        Bundle args = getArguments();
        if (args != null ) {
            FilmInfo film = (FilmInfo) args.getSerializable("FILM");
            Picasso.with(getActivity()).load(film.getPoster()).into(img_poster);
            tv_title.setText(film.getTitle());
            tv_plot.setText(film.getPlot());
        }








        return rootView;
    }
}
