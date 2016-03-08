package com.example.error.themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.error.themoviedb.service.DownloadService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    TextView trailView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView img_poster = (ImageView) rootView.findViewById(R.id.img_poster_view);
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tv_plot = (TextView) rootView.findViewById(R.id.tv_plot);
        trailView = (TextView) rootView.findViewById(R.id.traierls);
        return rootView;
    }




}
