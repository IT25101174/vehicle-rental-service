package com.vehiclerental.model;

public class User
{
    //Instance Variables
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    private String role;
    //Default Constructor
    public User()
    {}

    // Parameterized Constructor
    public User(int id, String name, String email, String password, String role)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }



    // Getters & Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Helper method to write to text file easily
    public String toFileString() {
        return id + "," + name + "," + email + "," + password + "," + role;
    }
}
