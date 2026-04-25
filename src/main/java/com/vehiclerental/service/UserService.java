package com.vehiclerental.service;

import com.vehiclerental.model.User;
import com.vehiclerental.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // IMPORTANT: We completely deleted the hardcoded FILE_PATH here!

    // 1. addUser now strictly uses the dynamic filePath
    public void addUser(User user, String filePath) {
        try {
            System.out.println("ATTENTION: Securely saving data to -> " + filePath);

            // Generate ID using the dynamic path
            int nextId = FileHandler.getNextId(filePath);
            user.setId(nextId);

            // Save the data using the dynamic path
            FileHandler.appendLine(filePath, user.toFileString());

            System.out.println("SUCCESS: File writing complete!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. getUsers now accepts the dynamic filePath
    public ArrayList<User> getUsers(String filePath) {
        ArrayList<User> users = new ArrayList<>();

        try {
            // Read lines from the dynamic path
            List<String> lines = FileHandler.readAll(filePath);

            for (String line : lines) {
                String[] data = line.split(",");

                // Rebuild the User object (Now with 5 parameters)
                User user = new User(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4] // Role field
                );

                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // 3. login accepts the dynamic filePath AND passes it to getUsers
    public boolean login(String email, String password, String filePath) {

        // Pass the map to getUsers so it knows where to read from!
        ArrayList<User> users = getUsers(filePath);

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}