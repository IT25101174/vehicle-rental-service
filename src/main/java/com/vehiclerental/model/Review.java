package com.vehiclerental.model;

public class Review {

    private int id;
    private int userId;
    private int vehicleId;
    private int rating;
    private String comment;
    private String type;

    // Constructor
    public Review(int id, int userId, int vehicleId, int rating, String comment, String type) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.rating = rating;
        this.comment = comment;
        this.type = type;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // This method will be overridden in subclasses (polymorphism!)
    public String displayReview() {
        return "Review by User " + userId + ": " + comment + " (" + rating + "/5)";
    }
}