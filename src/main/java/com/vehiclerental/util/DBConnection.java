/*package com.vehiclerental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // We will create the 'vehicle_rental' database in Step 2
    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_rental";
    private static final String USER = "root";
    private static final String PASSWORD = "root1234";

    private static Connection connection = null;

    // Private constructor prevents instantiation from other classes
    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}*/