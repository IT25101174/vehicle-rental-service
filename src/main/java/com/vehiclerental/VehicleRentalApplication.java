package com.vehiclerental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // This magic tag tells Spring Boot to look for your @WebServlet files!
public class VehicleRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleRentalApplication.class, args);
        System.out.println("🚗 Vehicle Rental System is running on http://localhost:8080");
    }
}











package com.vehiclerental.servlet;

import com.vehiclerental.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

// Servlet → handles HTTP requests
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
        }

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");

            service.update(id, name, desc);
        }
    }

    // HANDLE GET (view data)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().println(service.getAll());
    }
}
