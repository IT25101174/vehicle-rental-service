package com.vehiclerental.service;

import com.vehiclerental.model.User;
import com.vehiclerental.FileHandler; // Importing your shared utility!

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Pointing to the correct folder you set up
    private static final String FILE_PATH = "data/users.txt";

    // Save user to file
    public void addUser(User user) {
        try {
            // 1. Automatically calculate the next ID (1, 2, 3...)
            int nextId = FileHandler.getNextId(FILE_PATH);
            user.setId(nextId);

            // 2. Use the helper method from the User model to format the string
            // This safely writes: ID, Name, Email, Password, Role
            FileHandler.appendLine(FILE_PATH, user.toFileString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all users
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            // Read all lines using your FileHandler
            List<String> lines = FileHandler.readAll(FILE_PATH);

            for (String line : lines) {
                String[] data = line.split(",");

                // Rebuild the User object (Now with 5 parameters!)
                User user = new User(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4] // This is the Role field
                );

                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Login check
    public boolean login(String email, String password) {
        ArrayList<User> users = getUsers();

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}