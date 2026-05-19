package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    // Use the unified UserService instead of AdminService
    UserService userService = new UserService();

    // Handles Viewing Pages (The Dashboard)
    // Added ServletException for VS Code
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Force Admin Login validation to protect the dashboard from direct URL access!
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
            response.sendRedirect("adminLogin.html");
            return;
        }

        if ("dashboard".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/adminDashboard.jsp").forward(request, response);
        }
    }

    // Handles Form Submissions (Login and Register)
    // Added ServletException for VS Code
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        // Unified project-wide user storage
        String securePath = "data/users.txt";

        // 1. Handle Admin Registration
        if ("adminRegister".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role"); // Claude hardcoded this to "admin"

            User newAdmin = User.createUser(0, name, email, password, role);
            // Updated to use userService
            userService.addUser(newAdmin, securePath);

            // Send them to the login page after registering
            response.sendRedirect("adminLogin.html");
        }

        // 2. Handle Admin Login
        else if ("adminLogin".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
 
            // Use the unified validateUser method
            User admin = userService.validateUser(email, password, securePath);
 
            if (admin != null && "admin".equalsIgnoreCase(admin.getRole())) {
                // SUCCESS: Set up the session properly
                HttpSession session = request.getSession();
                session.setAttribute("userId", admin.getId());
                session.setAttribute("userName", admin.getName());
                session.setAttribute("role", admin.getRole());

                // Route to the dashboard
                response.sendRedirect("admin?action=dashboard");
            } else {
                // Failed! Send back to the login page with error flag
                response.sendRedirect("adminLogin.html?error=true");
            }
        }
    }
}