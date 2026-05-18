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

    //Handle get requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {

        // Get action parameter from url
        String action = request.getParameter("action");

        // user data file path
        String dynamicPath = "data/users.txt";

        if ("listUsers".equals(action))
        {
            // Get all users
            java.util.ArrayList<User> allUsers = service.getUsers(dynamicPath);

            // Send user list to jsp
            request.setAttribute("userList", allUsers);

            // Open userList.jsp
            request.getRequestDispatcher("/WEB-INF/views/userList.jsp").forward(request, response);
        }

        //edit user
        else if ("edit".equals(action))
        {
            // get user id from url
            int id = Integer.parseInt(request.getParameter("id"));
            // find user by id
            User user = service.getUserById(id, dynamicPath);
            //send user object to jsp
            request.setAttribute("user", user);
            //open edit page
            request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
        }

        //delete user
        else if ("delete".equals(action)) {
            //get user id
            int id = Integer.parseInt(request.getParameter("id"));
            service.deleteUser(id, dynamicPath);
            response.sendRedirect("user?action=listUsers");
        }

        else if ("addUserForm".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        }

        else if ("logout".equals(action))
        {
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

            // Encapsulated polymorphic creation using Factory Pattern
            User newUser = User.createUser(0, name, email, password, role);
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
                
                // Redirect based on role polymorphically! (Abstraction in action)
                response.sendRedirect(user.getDashboardRedirectURL());
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

            User updatedUser = User.createUser(id, name, email, password, role);
            service.updateUser(updatedUser, securePath);

            response.sendRedirect("user?action=listUsers");
        }
        else if ("adminAddUser".equals(action))
        {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            User newUser = User.createUser(0, name, email, password, role);
            service.addUser(newUser, securePath);
            
            response.sendRedirect("user?action=listUsers");
        }
    }
}

