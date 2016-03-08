package com.example.error.themoviedb;

import java.io.Serializable;

public class FilmItem implements Serializable {
    private int id;
    private String title;
    private String poster;
    private String plot;
    private double rating;
    private String release_date;
    private static String TAG = FilmItem.class.getSimpleName();

    public FilmItem(int id, String title, String poster, String plot, double rating, String release_date) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.rating = rating;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
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


    public interface MovieData {
        public void getOverviewInfo();
    }
}
