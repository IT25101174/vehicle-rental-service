package com.vehiclerental.service;

import com.vehiclerental.model.User;

import java.io.*;

import java.util.ArrayList;

public class UserService
{
    private static final String FILE_NAME = "users.txt";

    // Save user to file
    public void addUser(User user)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true)))
        {
            bw.write(user.getId() + "," + user.getName() + "," +
                    user.getEmail() + "," + user.getPassword());
            bw.newLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Get all users
    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                String[] data = line.split(",");

                User user = new User(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3]
                );

                users.add(user);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    // Login check
    public boolean login(String email, String password)
    {
        ArrayList<User> users = getUsers();

        for (User u : users)
        {
            if (u.getEmail().equals(email) && u.getPassword().equals(password))
            {
                return true;
            }
        }

        return false;
    }
}
