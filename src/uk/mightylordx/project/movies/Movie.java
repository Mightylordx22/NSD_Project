package uk.mightylordx.project.movies;

import java.util.List;

public class Movie {

    private final String movieID;
    private final String title;
    private final String releaseDate;
    private final String urlLink;
    private final List<String> genres;

    public Movie(String movieID, String title, List<String> genres, String releaseDate, String urlLink) {
        this.movieID = movieID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.urlLink = urlLink;
        this.genres = genres;
    }

    private String getMovieID() {
        return this.movieID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getUrlLink() {
        return this.urlLink;
    }

    public List<String> getGenreList() {
        return this.genres;
    }

}
