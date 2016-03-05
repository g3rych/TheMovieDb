package com.example.error.themoviedb.data;

import android.provider.BaseColumns;

public final class DBContract {
    public static abstract class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_id = "movie_id";
        public static final String TITLE = "title";
        public static final String PLOT = "plot";
        public static final String POSTER = "poster";
        public static final String RELEASE_DATE = "release_date";
        public static final String RATING = "rating";
    }
}
