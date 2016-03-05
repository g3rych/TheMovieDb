package com.example.error.themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.error.themoviedb.service.DownloadService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private ArrayList<String> trailerList;
    TextView trailView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView img_poster = (ImageView) rootView.findViewById(R.id.img_poster_view);
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_plot = (TextView) rootView.findViewById(R.id.tv_plot);
        trailView = (TextView) rootView.findViewById(R.id.traierls);
        FilmInfo film;

        Bundle args = getArguments();

            film = (FilmInfo) args.getSerializable("FILM");
            Picasso.with(getActivity()).load(film.getPoster()).into(img_poster);
            tv_title.setText(film.getTitle());
            tv_plot.setText(film.getPlot());

        final String URL = "http://api.themoviedb.org/3/movie/" + film.getId() +
                "?api_key=6dece3ed1b9e1950498be7673d071bdf" +
                "&language=en" +
                "&append_to_response=trailers";
        Intent intent = new Intent(getActivity(), DownloadService.class);
        intent.setData(Uri.parse(URL));
        intent.putExtra("isDetails",true);
        getActivity().startService(intent);


        IntentFilter filter = new IntentFilter("trailers");
        ResponseReciever reciever = new ResponseReciever();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(reciever, filter);



        return rootView;
    }
    private class ResponseReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            trailerList = (ArrayList<String>) intent.getSerializableExtra("trailers");
            trailView.setText(trailerList.get(0));
         }
    }



}
