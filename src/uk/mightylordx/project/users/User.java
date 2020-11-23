package uk.mightylordx.project.users;

import uk.mightylordx.project.ratings.Rating;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String userID;
    private final char gender;
    private final int age;
    private final String occupation;
    private final String zipCode;
    private final List<Rating> moviesRated;

    public User(String userID, int age, char gender, String occupation, String zipCode) {
        this.userID = userID;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.zipCode = zipCode;
        this.moviesRated = new ArrayList<>();
    }

    public String getUserID() {
        return this.userID;
    }

    public char getGender() {
        return this.gender;
    }

    public int getAge() {
        return this.age;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public List<Rating> getMoviesRated() {
        return this.moviesRated;
    }

    public void addRating(Rating r) {
        this.moviesRated.add(r);
    }

}
