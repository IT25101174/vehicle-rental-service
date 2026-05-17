package com.vehiclerental.model;

public class Review {

    private int id;
    private int userId;
    private int vehicleId;
    private int rating;
    private String comment;
    private String type;
    private String userName; // Dynamically resolved!

    // Constructor with built-in comma sanitization & User name dynamic database lookup!
    public Review(int id, int userId, int vehicleId, int rating, String comment, String type) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.rating = rating;
        this.comment = comment != null ? comment.replace(",", ";") : "";
        this.type = type != null ? type : "public";

        // Dynamic database lookup to resolve User Name from ID!
        this.userName = "User #" + userId;
        try {
            com.vehiclerental.service.UserService userService = new com.vehiclerental.service.UserService();
            com.vehiclerental.model.User user = userService.getUserById(userId, "data/users.txt");
            if (user != null) {
                this.userName = user.getName();
            }
        } catch (Exception e) {
            // Keep safe fallback "User #id"
        }
    }

    // Getters and setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public int getUserId() { 
        return userId; 
    }
    
    public void setUserId(int userId) { 
        this.userId = userId; 
    }
    
    public int getVehicleId() { 
        return vehicleId; 
    }
    
    public void setVehicleId(int vehicleId) { 
        this.vehicleId = vehicleId; 
    }
    
    public int getRating() { 
        return rating; 
    }
    
    public void setRating(int rating) { 
        this.rating = rating; 
    }
    
    public String getComment() { 
        return comment; 
    }
    
    public void setComment(String comment) { 
        this.comment = comment != null ? comment.replace(",", ";") : ""; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public void setType(String type) { 
        this.type = type; 
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Dynamic Polymorphic Method — Overridden by concrete subclasses
    public String displayReview() {
        return "Review by " + userName + ": " + comment + " (" + rating + "/5 stars)";
    }
}