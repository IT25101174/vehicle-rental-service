package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    // Initialize your new service
    AdminService adminService = new AdminService();

    // Handles Viewing Pages (The Dashboard)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("dashboard".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/adminDashboard.jsp").forward(request, response);
        }
    }

    // Handles Form Submissions (Login and Register)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        /*String securePath = getServletContext().getRealPath("/WEB-INF/data/users.txt");*/
        // Grabs the exact folder location of your project on your hard drive
        String projectRoot = System.getProperty("user.dir");

// Forces Java to write directly into your actual source code folder!
        String securePath = projectRoot + "/src/main/webapp/WEB-INF/data/users.txt";

        // 1. Handle Admin Registration
        if ("adminRegister".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role"); // Claude hardcoded this to "admin"

            User newAdmin = new User(0, name, email, password, role);
            adminService.addAdmin(newAdmin, securePath);

            // Send them to the login page after registering
            response.sendRedirect("adminLogin.html");
        }

        // 2. Handle Admin Login
        else if ("adminLogin".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Use the strict admin-only check
            boolean isAuthenticated = adminService.authenticateAdmin(email, password, securePath);

            if (isAuthenticated) {
                // Success! Route to the dashboard via doGet
                response.sendRedirect("/admin?action=dashboard");
            } else {
                // Failed! Send back to the custom login page with the error flag Claude built
                response.sendRedirect("adminLogin.html?error=true");
            }
        }
    }
}