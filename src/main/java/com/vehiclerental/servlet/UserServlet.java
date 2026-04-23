package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet
{
    UserService service = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String action = request.getParameter("action");

        if ("register".equals(action))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User(id, name, email, password);
            service.addUser(user);

            response.getWriter().println("Registered Successfully");
        }
        else if ("login".equals(action))
        {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean ok = service.login(email, password);

            if (ok)
                response.getWriter().println("Login Success");
            else
                response.getWriter().println("Login Failed");
        }
    }
}