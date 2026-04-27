package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/register")
public class UserServlet extends HttpServlet
{
    UserService service = new UserService();

    // Add this right above or below your doPost method in UserServlet.java
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        String action = request.getParameter("action");

        // Grab the same dynamic path we used earlier!
        String dynamicPath = getServletContext().getRealPath("/data/users.txt");

        if ("listUsers".equals(action)) {
            // 1. Fetch all users from the text file
            java.util.ArrayList<User> allUsers = service.getUsers(dynamicPath);

            // 2. Attach the list to the request (like putting a package in a delivery truck)
            request.setAttribute("userList", allUsers);

            // 3. Forward the truck into the secure WEB-INF vault to the JSP page
            request.getRequestDispatcher("/WEB-INF/userList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String action = request.getParameter("action");
        String securePath = getServletContext().getRealPath("/WEB-INF/data/users.txt");
        if ("register".equals(action))
        {
// We don't grab ID from the form anymore, the Service handles that!
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

// Grab the missing 5th parameter from the HTML dropdown
            String role = request.getParameter("role");

// Build the User with 5 parameters (passing 0 for the ID placeholder)
            User user = new User(0, name, email, password, role);
            service.addUser(user, securePath);

            response.sendRedirect("login.html");
        }
        else if ("login".equals(action))
        {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean ok = service.login(email, password, securePath);

            if (ok)
                response.getWriter().println("Login Success");
            else
                response.getWriter().println("Login Failed");
        }
    }
}


/*package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/register")
public class UserServlet extends HttpServlet {

    UserService service = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        String action = request.getParameter("action");

        // FIX 1: Synced this path to match the secure WEB-INF path used in doPost
        String securePath = getServletContext().getRealPath("/WEB-INF/data/users.txt");

        if ("listUsers".equals(action)) {
            // 1. Fetch all users from the text file
            java.util.ArrayList<User> allUsers = service.getUsers(securePath);

            // 2. Attach the list to the request
            request.setAttribute("userList", allUsers);

            // 3. Forward into the secure WEB-INF vault to the JSP page
            request.getRequestDispatcher("/WEB-INF/userList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        // Secure text file database location
        String securePath = getServletContext().getRealPath("/WEB-INF/data/users.txt");

        if ("register".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            // Build the User with 5 parameters (passing 0 for the ID placeholder)
            User user = new User(0, name, email, password, role);
            service.addUser(user, securePath);

            response.sendRedirect("login.html");
        }
        else if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean ok = service.login(email, password, securePath);

            if (ok) {
                // FIX 2: Routing successful logins to your newly created Admin Dashboard!
                // Note: Since service.login returns a boolean right now, this sends EVERYONE to the admin dashboard.
                // Once you update your UserService to return a User object, you can add an if-statement here to check the role!
                response.sendRedirect("/admin?action=dashboard");
            } else {
                response.sendRedirect("login.html?error=true");
            }
        }
    }
}*/