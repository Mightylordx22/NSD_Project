package uk.mightylordx.project.users;

public class User {

    private final String userID;
    private final char gender;
    private final int age;
    private final String occupation;
    private final String zipCode;

    public User(String userID, int age, char gender, String occupation, String zipCode) {
        this.userID = userID;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.zipCode = zipCode;
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

}
