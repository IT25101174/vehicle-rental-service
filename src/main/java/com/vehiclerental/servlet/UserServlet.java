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