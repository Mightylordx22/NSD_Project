package uk.mightylordx.project.solution;

import uk.mightylordx.project.exceptions.FileWriteException;
import uk.mightylordx.project.exceptions.IncorrectFileException;
import uk.mightylordx.project.movies.Movie;
import uk.mightylordx.project.ratings.Rating;
import uk.mightylordx.project.users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Solution {

    private final String[] availableGenres = new String[]{"Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"};

    private final HashMap<String, User> userList;
    private final HashMap<String, List<Rating>> userRatingList;
    private final HashMap<String, List<Rating>> movieRatingList;
    private final HashMap<String, Movie> movieList;

    public Solution() {
        this.userRatingList = new HashMap<>();
        this.movieRatingList = new HashMap<>();
        this.userList = new HashMap<>();
        this.movieList = new HashMap<>();
    }

    private void populateRatingsList() {
        try {
            File f = new File("data\\ratings.dat");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                List<String> temp = new ArrayList<>(Arrays.asList(s.nextLine().strip().split("\t")));
                this.userRatingList.put(temp.get(0), new ArrayList<>());
                this.movieRatingList.put(temp.get(1), new ArrayList<>());
            }
            s = new Scanner(f);
            while (s.hasNextLine()) {
                List<String> temp = new ArrayList<>(Arrays.asList(s.nextLine().strip().split("\t")));
                Rating r = new Rating(temp.get(0), temp.get(1), Integer.parseInt(temp.get(2)), Long.parseLong(temp.get(3)));
                List<Rating> uData = this.userRatingList.get(r.getUserID());
                List<Rating> mData = this.movieRatingList.get(r.getMovieID());
                uData.add(r);
                mData.add(r);
                this.userRatingList.put(r.getUserID(), uData);
                this.movieRatingList.put(r.getMovieID(), mData);
            }
        } catch (FileNotFoundException e) {
            throw new IncorrectFileException("Ratings File not Found", e);
        }
    }

    private void populateUserList() {
        boolean duplicate = false;
        try {
            File f = new File("data\\users.dat");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                List<String> temp = new ArrayList<>(Arrays.asList(s.nextLine().strip().split("\\|")));
                User u = new User(temp.get(0), Integer.parseInt(temp.get(1)), temp.get(2).charAt(0), temp.get(3), temp.get(4));
                if (this.userList.containsKey(temp.get(0))) {
                    System.out.printf("User: %s already exists%n", temp.get(0));
                    String temp2 = Integer.toString(Integer.parseInt(u.getUserID()) + 943);
                    this.userList.put(temp2, u);
                    duplicate = true;
                } else {
                    this.userList.put(u.getUserID(), u);
                }
            }
            if (!duplicate) {
                System.out.println("\n[There were no duplicate UserIDs]\n");
            } else {
                System.out.println("\n[There were some duplicate userIDs [See Above]]\n");
            }
        } catch (FileNotFoundException e) {
            throw new IncorrectFileException("User File not Found", e);
        }
    }

    private void populateMoviesList() {
        boolean duplicate = false;
        try {
            File f = new File("data\\movies.dat");
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {

                List<String> temp = new ArrayList<>(Arrays.asList(s.nextLine().strip().split("\\|")));
                temp.remove(3);
                List<String> possibleGenres = new ArrayList<>(temp.subList(5, temp.size()));
                List<String> genres = new ArrayList<>();
                for (int i = 0; i < possibleGenres.size(); i++) {
                    if (possibleGenres.get(i).equals("1")) {
                        genres.add(availableGenres[i]);
                    }
                }
                Movie m = new Movie(temp.get(0), temp.get(1), genres, temp.get(2), temp.get(3));
                if (this.movieList.containsKey(temp.get(0))) {
                    System.out.printf("Movie: %s already exists%n", temp.get(0));
                    int temp2 = Integer.parseInt(temp.get(0)) + 1682;
                    this.movieList.put(Integer.toString(temp2), m);
                    duplicate = true;
                } else {
                    this.movieList.put(temp.get(0), m);
                }
            }
            if (!duplicate) {
                System.out.println("\n[There were no duplicate MovieIDs]\n");
            } else {
                System.out.println("\n[There were some duplicate MovieIDs [See Above]]\n");
            }
        } catch (FileNotFoundException e) {
            throw new IncorrectFileException("Movie File not Found", e);
        }
    }

    private void printUserRating(String userID) {
        if (!this.userRatingList.containsKey(userID)) {
            System.out.printf("Sorry %s is not a valid UserID%n", userID);
        } else {
            int total = this.userRatingList.get(userID).size();
            float score = 0;
            for (Rating r : this.userRatingList.get(userID)) {
                score += r.getRating();
            }
            System.out.printf("UserID: %s | Average Rating: %.2f%n", userID, score / total);
            try {
                File file = new File("outputFiles\\oneUserRating.txt");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(String.format("UserID: %s | Average Rating: %.2f%n", userID, score / total));
                fileWriter.close();
            } catch (Exception e) {
                throw new FileWriteException("Cannot Write to File oneUserRating.txt", e);
            }
        }
    }

    private void printAllUserRatings() {
        try {
            File file = new File("outputFiles\\allUserRating.txt");
            FileWriter fileWriter = new FileWriter(file);
            for (Map.Entry<String, List<Rating>> hash : userRatingList.entrySet()) {
                List<Rating> data = hash.getValue();
                int total = data.size();
                float score = 0;
                for (Rating r : data) {
                    score += r.getRating();
                }
                System.out.printf("UserID: %s | Average Rating: %.2f%n", hash.getKey(), score / total);
                fileWriter.append(String.format("UserID: %s | Average Rating: %.2f%n", hash.getKey(), score / total));
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new FileWriteException("Cannot Write to File allUserRating.txt", e);
        }
    }

    private void printMoveRating(String movieID) {
        if (!movieRatingList.containsKey(movieID)) {
            System.out.printf("Sorry %s is not a valid MovieID%n", movieID);
        } else {
            int total = movieRatingList.get(movieID).size();
            float score = 0;
            for (Rating r : movieRatingList.get(movieID)) {
                score += r.getRating();
            }
            System.out.printf("MovieID: %s | Movie Title: %s | Average Rating: %.2f%n", movieID, this.movieList.get(movieID).getTitle(), score / total);
            try {
                File file = new File("outputFiles\\oneMovieRating.txt");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(String.format("MovieID: %s | Movie Title: %s | Average Rating: %.2f%n", movieID, this.movieList.get(movieID).getTitle(), score / total));
                fileWriter.close();
            } catch (Exception e) {
                throw new FileWriteException("Cannot Write to File oneMovieRating.txt", e);
            }
        }
    }

    private void printAllMovieRatings() {
        try {
            File file = new File("outputFiles\\allMovieRating.txt");
            FileWriter fileWriter = new FileWriter(file);
            for (Map.Entry<String, List<Rating>> hash : movieRatingList.entrySet()) {
                List<Rating> data = hash.getValue();
                int total = data.size();
                float score = 0;
                for (Rating r : data) {
                    score += r.getRating();
                }
                System.out.printf("MovieID: %s | Movie Title: %s | Average Rating: %.2f%n", hash.getKey(), this.movieList.get(hash.getKey()).getTitle(), score / total);
                fileWriter.append(String.format("MovieID: %s | Movie Title: %s | Average Rating: %.2f%n", hash.getKey(), this.movieList.get(hash.getKey()).getTitle(), score / total));
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new FileWriteException("Cannot Write to File allMovieRating.txt", e);
        }
    }

    private void printTwoRatedMovies(String userIDOne, String userIDTwo) {
        try {
            if (this.userRatingList.containsKey(userIDOne) && this.userRatingList.containsKey(userIDTwo)) {
                boolean rated = false;
                List<String> firstUserMovieList = new ArrayList<>();
                File file = new File("outputFiles\\sharedMovies.txt");
                FileWriter fileWriter = new FileWriter(file);
                for (Rating r : this.userRatingList.get(userIDOne)) {
                    firstUserMovieList.add(this.movieList.get(r.getMovieID()).getTitle());
                }
                for (Rating r : this.userRatingList.get(userIDTwo)) {
                    String title = this.movieList.get(r.getMovieID()).getTitle();
                    if (firstUserMovieList.contains(title)) {
                        System.out.printf("User [%s] and [%s] have both rated %s%n", userIDOne, userIDTwo, title);
                        fileWriter.append(String.format("User [%s] and [%s] have both rated %s%n", userIDOne, userIDTwo, title));
                        rated = true;
                    }
                }
                if (!rated) {
                    System.out.printf("Users [%s] and [%s] do not share any movies%n", userIDOne, userIDTwo);
                    fileWriter.append(String.format("Users [%s] and [%s] do not share any movies%n", userIDOne, userIDTwo));
                }
                fileWriter.close();
            } else {
                System.out.println("Sorry one of those UserID's are not right.");
            }
        } catch (Exception e) {
            throw new FileWriteException("Cannot Write to File sharedMovies.txt", e);
        }
    }

    public void getUserOption() {
        boolean carryOn = true;
        boolean loadedInformation = false;
        while (carryOn) {
            System.out.print("----------------------------\n| Please choose from below |\n----------------------------\nA] Load all information\nB] Show average rating of specific user\nC] Show average ratings of specific movie\nD] Show all user ratings\nE] Show all movie ratings\nF] Find movies rated by 2 users\nX] Quit\n>>> ");
            Scanner s = new Scanner(System.in);
            s.useDelimiter("[;\\r\\n]+");
            String userOption = s.nextLine().toUpperCase();
            switch (userOption.strip()) {
                case "A" -> {
                    if (!loadedInformation) {
                        this.populateUserList();
                        this.populateMoviesList();
                        this.populateRatingsList();
                        System.out.println("All the information has been loaded!");
                        loadedInformation = true;
                    } else {
                        System.out.println("You have already loaded the information!");
                    }
                }
                case "B" -> {
                    if (!loadedInformation) {
                        System.out.println("Please load the information first!");
                    } else {
                        Scanner s1 = new Scanner(System.in);
                        System.out.print("Please enter a userID: ");
                        String userID = s1.nextLine();
                        this.printUserRating(userID);
                    }

                }
                case "C" -> {
                    if (!loadedInformation) {
                        System.out.println("Please load the information first");
                    } else {
                        Scanner s1 = new Scanner(System.in);
                        System.out.print("Please enter a movieID: ");
                        String movieID = s1.nextLine();
                        this.printMoveRating(movieID);
                    }
                }
                case "D" -> {
                    if (!loadedInformation) {
                        System.out.println("Please load the information first!");
                    } else {
                        this.printAllUserRatings();
                    }
                }
                case "E" -> {
                    if (!loadedInformation) {
                        System.out.println("Please load the information first!");
                    } else {
                        this.printAllMovieRatings();
                    }
                }
                case "F" -> {
                    if (!loadedInformation) {
                        System.out.println("Please load the information first!");
                    } else {
                        Scanner s1 = new Scanner(System.in);
                        System.out.print("Please enter a userID: ");
                        String firstUserID = s1.nextLine();
                        System.out.print("Please enter another userID: ");
                        String secondUserID = s1.next();
                        this.printTwoRatedMovies(firstUserID, secondUserID);
                    }
                }
                case "X" -> {
                    carryOn = false;
                    System.out.println("Thanks for using this software!");
                }
                default -> System.out.println("Sorry that is not a valid option!");
            }

        }
    }

}
