package com.example.error.themoviedb;

import android.util.Log;

import java.io.Serializable;

public class FilmInfo implements Serializable {
    private String title;
    private String poster;
    private String plot;
    private double rating;
    private String release_date;
    private static String TAG = FilmInfo.class.getSimpleName();

    public FilmInfo(String title, String poster, String plot, double rating, String release_date) {
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.release_date = release_date;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
