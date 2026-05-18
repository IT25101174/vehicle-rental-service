package com.vehiclerental.service;

import com.vehiclerental.model.User;
import com.vehiclerental.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService
{


    //add new user
    public void addUser(User user, String filePath)
    {
        try
        {
            //dispaly file path
            System.out.println("ATTENTION : Securely saving data to -> " + filePath);

            //generate nest available id
            int nextId = FileHandler.getNextId(filePath);
            //set generated id to user object
            user.setId(nextId);

            //save user data
            FileHandler.appendLine(filePath, user.toFileString());

            //done message
            System.out.println("SUCCESS : File writing complete!");

        }
        catch (IOException e)
        {
            //print error if operation fail
            e.printStackTrace();
        }
    }

    //read all users
    public ArrayList<User> getUsers(String filePath)
    {
        //create array list to store users
        ArrayList<User> users = new ArrayList<>();

        try
        {
            //read all lines
            List<String> lines = FileHandler.readAll(filePath);

            //loop through each line
            for (String line : lines)
            {
                //split line
                String[] data = line.split(",");

                //create user object
                User user = User.createUser
                        (Integer.parseInt(data[0]), //id
                        data[1], //name
                        data[2], //email
                        data[3], //password
                        data[4] //role
                );
                //add usre object to list
                users.add(user);
            }
        }

        catch (IOException e)
        {
            //print erroe if reading fail
            e.printStackTrace();
        }
        return users;
    }

    //login validation, check email & pw is correct
    public boolean login(String email, String password, String filePath)
    {

        // Pass the map to getUsers so it knows where to read from!
        ArrayList<User> users = getUsers(filePath);

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // 3. login returns the full User object if successful
    public User validateUser(String email, String password, String filePath) {
        ArrayList<User> users = getUsers(filePath);
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // 4. getUserById
    public User getUserById(int id, String filePath) {
        ArrayList<User> users = getUsers(filePath);
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // 5. updateUser (UPDATE)
    public void updateUser(User updatedUser, String filePath) {
        try {
            List<String> lines = FileHandler.readAll(filePath);
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[0]);

                if (userId == updatedUser.getId()) {
                    newLines.add(updatedUser.toFileString());
                } else {
                    newLines.add(line);
                }
            }
            FileHandler.writeAll(filePath, newLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 6. deleteUser (DELETE)
    public void deleteUser(int id, String filePath) {
        try {
            List<String> lines = FileHandler.readAll(filePath);
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[0]);

                if (userId != id) {
                    newLines.add(line);
                }
            }
            FileHandler.writeAll(filePath, newLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}