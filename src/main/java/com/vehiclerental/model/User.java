package com.vehiclerental.model;

public abstract class User
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

    // Abstract method to demonstrate Abstraction & Polymorphism
    public abstract String getDashboardRedirectURL();

    // Static Factory Method for encapsulated polymorphic instantiation
    public static User createUser(int id, String name, String email, String password, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return new AdminUser(id, name, email, password, "SystemAdmin");
        } else {
            return new CustomerUser(id, name, email, password, "Standard");
        }
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
