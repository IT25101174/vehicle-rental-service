package com.vehiclerental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Update these with your local MySQL credentials
    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_rental";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;
//test
    // Private constructor to prevent instantiation (Singleton pattern)
    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Database connection failed.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}