package com.example.error.themoviedb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.error.themoviedb.service.ServiceHelper;


public class MainActivity extends AppCompatActivity implements
                                    MainFragment.CallBack {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceHelper.getInstance().clearDB(this);
        ServiceHelper.getInstance().gridViewNextPage(this);
    }

    @Override
    public void onItemSelected(Uri uri,int movie_id) {
        ServiceHelper.getInstance().getTrailers(this, movie_id);
        Log.d(TAG,uri.toString());
        Intent intent  = new Intent(this, DetailActivity.class);
        intent.setData(uri);
        intent.putExtra("movie_id",movie_id);
        startActivity(intent);

    }
}

