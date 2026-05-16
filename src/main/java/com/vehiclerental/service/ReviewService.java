package com.vehiclerental.service;

import com.vehiclerental.FileHandler;
import com.vehiclerental.model.Review;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    private static final String FILE_PATH = "data/reviews.txt";

    // CREATE — add a new review
    public void addReview(int userId, int vehicleId, int rating, String comment, String type) throws IOException {
        int id = FileHandler.getNextId(FILE_PATH);
        String line = id + "," + userId + "," + vehicleId + "," + rating + "," + comment + "," + type;
        FileHandler.appendLine(FILE_PATH, line);
    }

    // READ — get all reviews
    public List<Review> getAllReviews() throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<Review> reviews = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            Review review = new Review(
                    Integer.parseInt(parts[0]),  // id
                    Integer.parseInt(parts[1]),  // userId
                    Integer.parseInt(parts[2]),  // vehicleId
                    Integer.parseInt(parts[3]),  // rating
                    parts[4],                    // comment
                    parts[5]                     // type
            );
            reviews.add(review);
        }
        return reviews;
    }

    // READ — get reviews for a specific vehicle
    public List<Review> getReviewsByVehicle(int vehicleId) throws IOException {
        List<Review> all = getAllReviews();
        List<Review> filtered = new ArrayList<>();
        for (Review r : all) {
            if (r.getVehicleId() == vehicleId) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    // DELETE — remove a review by id
    public void deleteReview(int reviewId) throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) != reviewId) {
                updated.add(line);  // keep all except the one to delete
            }
        }
        FileHandler.writeAll(FILE_PATH, updated);
    }

    // UPDATE — edit a review's comment and rating
    public void updateReview(int reviewId, int newRating, String newComment) throws IOException {
        List<String> lines = FileHandler.readAll(FILE_PATH);
        List<String> updated = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == reviewId) {
                // Replace this line with updated values
                String newLine = parts[0] + "," + parts[1] + "," + parts[2] + "," + newRating + "," + newComment + "," + parts[5];
                updated.add(newLine);
            } else {
                updated.add(line);
            }
        }
        FileHandler.writeAll(FILE_PATH, updated);
    }
}