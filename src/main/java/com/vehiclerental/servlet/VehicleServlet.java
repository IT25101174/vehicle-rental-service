package com.vehiclerental.servlet;

import com.vehiclerental.model.Vehicle;
import com.vehiclerental.service.VehicleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/vehicle")
public class VehicleServlet extends HttpServlet {

    private VehicleService vehicleService = new VehicleService();

    // Handles GET requests (view, edit, delete, search)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---> THE FIX: Grab the secure server path here <---
        String securePath = getServletContext().getRealPath("/WEB-INF/data/vehicles.txt");

        // Get the action parameter from URL
        String action = request.getParameter("action");

        // If action is null, default to list
        if (action == null) {
            action = "list";
        }

        // Decide which function to execute based on action
        switch (action) {

            //List all vehicles
            case "list":
                // Passed securePath to the service
                List<Vehicle> vehicles = vehicleService.getAllVehicles(securePath);

                // Send vehicle list to JSP page
                request.setAttribute("vehicles", vehicles);

                // Forward request to vehicles.jsp
                request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp")
                        .forward(request, response);
                break;

            // Edit vehicle(show form)
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));

                // Passed securePath to the service
                Vehicle vehicle = vehicleService.getVehicleById(id, securePath);

                // Send vehicle object to editVehicle.jsp
                request.setAttribute("vehicle", vehicle);

                // Forward to edit page
                request.getRequestDispatcher("/WEB-INF/views/editVehicle.jsp")
                        .forward(request, response);
                break;

            //Delete vehicle
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));

                // Passed securePath to the service
                vehicleService.deleteVehicle(deleteId, securePath);

                // Redirect back to vehicle list page
                response.sendRedirect("vehicle?action=list");
                break;

            //Search vehicles
            case "search":
                String keyword = request.getParameter("keyword");

                // Passed securePath to the service
                List<Vehicle> searchResults = vehicleService.searchVehicles(keyword, securePath);

                // Send results to JSP page
                request.setAttribute("vehicles", searchResults);

                // Forward results to vehicles.jsp
                request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp")
                        .forward(request, response);
                break;

            // Default
            default:
                response.sendRedirect("vehicle?action=list");
        }
    }

    // Handles POST requests (add, update)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---> THE FIX: Grab the secure server path here too <---
        String securePath = getServletContext().getRealPath("/WEB-INF/data/vehicles.txt");

        // Get action from form submission
        String action = request.getParameter("action");

        // If action is null, default to add
        if (action == null) {
            action = "add";
        }

        switch (action) {

            // Add vehicle
            case "add":
                String brand = request.getParameter("brand");
                String type = request.getParameter("type");
                double pricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
                boolean available = Boolean.parseBoolean(request.getParameter("available"));

                // Create Vehicle object from form data
                Vehicle newVehicle = new Vehicle(0, brand, type, pricePerDay, available);

                // Passed securePath to the service
                vehicleService.addVehicle(newVehicle, securePath);

                // Redirect to list page after adding
                response.sendRedirect("vehicle?action=list");
                break;

            // Update vehicle
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                String uBrand = request.getParameter("brand");
                String uType = request.getParameter("type");
                double uPricePerDay = Double.parseDouble(request.getParameter("pricePerDay"));
                boolean uAvailable = Boolean.parseBoolean(request.getParameter("available"));

                // Create updated Vehicle object
                Vehicle updatedVehicle = new Vehicle(id, uBrand, uType, uPricePerDay, uAvailable);

                // Passed securePath to the service
                vehicleService.updateVehicle(updatedVehicle, securePath);

                // Redirect back to list page
                response.sendRedirect("vehicle?action=list");
                break;

            // Default
            default:
                response.sendRedirect("vehicle?action=list");
        }
    }
}