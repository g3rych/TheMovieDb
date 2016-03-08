package com.example.error.themoviedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_detail);



        FilmItem film = (FilmItem) getIntent().getSerializableExtra("FILM");

        Bundle arg = new Bundle();
        arg.putSerializable("FILM", film);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arg);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container,fragment)
                .commit();





    }
}
