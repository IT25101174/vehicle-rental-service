package com.vehiclerental.servlet;

import com.vehiclerental.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // Added this import
import jakarta.servlet.http.*;
import java.io.IOException;

// Added the WebServlet annotation so the HTML forms can actually find it!
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private CategoryService service = new CategoryService();

    // HANDLE POST (add/update)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");

            service.addCategory(name, desc);

            // FIX: Send the user back to the dashboard after adding!
            // (Punara might need to change this URL depending on what he named his HTML/JSP file)
            response.sendRedirect("addCategory.html?success=true");
        }

        else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");

            service.update(id, name, desc);

            // FIX: Send the user back after updating!
            response.sendRedirect("viewCategories.html?updated=true");
        }
    }

    // HANDLE GET (view data)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Note for Punara: This just dumps text to the screen for testing.
        // Later, you need to use request.setAttribute() and forward to a JSP page!
        response.getWriter().println(service.getAll());
    }
}