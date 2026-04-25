package com.vehiclerental.model;

public class AdminUser extends User {

    private String adminRole;

    // The updated constructor
    public AdminUser(int id, String name, String email, String password, String adminRole) {
        // The Fix: Pass the 4 variables UP to the parent,
        // AND hardcode the 5th variable (role) as "admin"!
        super(id, name, email, password, "admin");
        this.adminRole = adminRole;
    }

    public String getAdminRole() { return adminRole; }

    public void setAdminRole(String adminRole) { this.adminRole = adminRole; }

    public void manageSystem() {
        System.out.println("Admin " + getName() + " is managing the system.");
    }
}