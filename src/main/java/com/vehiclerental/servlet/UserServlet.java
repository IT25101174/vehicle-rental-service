package com.vehiclerental.servlet;

import com.vehiclerental.model.User;
import com.vehiclerental.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet
{
    UserService service = new UserService();

    // Updated with ServletException for VS Code compilation
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        String action = request.getParameter("action");

        // Grab the same dynamic path we used earlier!
        String dynamicPath = "data/users.txt";

        if ("listUsers".equals(action)) {
            // 1. Fetch all users from the text file
            java.util.ArrayList<User> allUsers = service.getUsers(dynamicPath);

            // 2. Attach the list to the request (like putting a package in a delivery truck)
            request.setAttribute("userList", allUsers);

            // 3. Forward the truck into the secure WEB-INF vault to the JSP page
            request.getRequestDispatcher("/WEB-INF/views/userList.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = service.getUserById(id, dynamicPath);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.deleteUser(id, dynamicPath);
            response.sendRedirect("user?action=listUsers");
        } else if ("addUserForm".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        } else if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, jakarta.servlet.ServletException
    {
        String action = request.getParameter("action");
        String securePath = "data/users.txt";
        if ("register".equals(action))
        {
// We don't grab ID from the form anymore, the Service handles that!
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

// Grab the missing 5th parameter from the HTML dropdown
            String role = request.getParameter("role");

            com.vehiclerental.model.User newUser = new com.vehiclerental.model.User(0, name, email, password, role);
            service.addUser(newUser, securePath);
            
            response.sendRedirect("login.html?success=true");
        } 
        // 2. Handle Login
        else if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
 
            User user = service.validateUser(email, password, securePath);
 
            if (user != null) {
                // Store user info in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("role", user.getRole());
                
                // Redirect based on role
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    // Redirect to the Admin URL instead of forwarding internally
                    response.sendRedirect("admin?action=dashboard");
                } else {
                    // Customers go to the dynamic homepage
                    response.sendRedirect("index.jsp");
                }
            } else {
                response.sendRedirect("login.html?error=invalid");
            }
        }
        else if ("update".equals(action))
        {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            String role = request.getParameter("role");
            if (role == null || role.isEmpty()) {
                User oldUser = service.getUserById(id, securePath);
                role = (oldUser != null) ? oldUser.getRole() : "customer";
            }

            User updatedUser = new User(id, name, email, password, role);
            service.updateUser(updatedUser, securePath);

            response.sendRedirect("user?action=listUsers");
        }
        else if ("adminAddUser".equals(action))
        {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            com.vehiclerental.model.User newUser = new com.vehiclerental.model.User(0, name, email, password, role);
            service.addUser(newUser, securePath);
            
            response.sendRedirect("user?action=listUsers");
        }
    }
}

