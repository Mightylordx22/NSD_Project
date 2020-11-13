package uk.mightylordx.project.ratings;

public class Rating {

    private final String userID;
    private final String movieID;
    private final int rating;
    private final long time;


    public Rating(String userID, String movieID, int rating, long time) {
        this.userID = userID;
        this.movieID = movieID;
        this.rating = rating;
        this.time = time;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getMovieID() {
        return this.movieID;
    }

    public int getRating() {
        return this.rating;
    }

}
