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
                    List<Vehicle> vehicles = vehicleService.getAllVehicles();

                    // Send vehicle list to JSP page
                    request.setAttribute("vehicles", vehicles);

                    // Forward request to vehicles.jsp
                    request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp")
                            .forward(request, response);
                    break;

                // Edit vehicle(show form)
                case "edit":
                    int id = Integer.parseInt(request.getParameter("id"));

                    // Get the vehicle record by ID
                    Vehicle vehicle = vehicleService.getVehicleById(id);

                    // Send vehicle object to editVehicle.jsp
                    request.setAttribute("vehicle", vehicle);

                    // Forward to edit page
                    request.getRequestDispatcher("/WEB-INF/views/editVehicle.jsp")
                            .forward(request, response);
                    break;

                //Delete vehicle
                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));

                    // Delete vehicle from file
                    vehicleService.deleteVehicle(deleteId);

                    // Redirect back to vehicle list page
                    response.sendRedirect("vehicle?action=list");
                    break;

                //Search vehicles
                case "search":
                    String keyword = request.getParameter("keyword");

                    // Search vehicles
                    List<Vehicle> searchResults = vehicleService.searchVehicles(keyword);

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

                    // Save new vehicle into file
                    vehicleService.addVehicle(newVehicle);

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

                    // Update record in file
                    vehicleService.updateVehicle(updatedVehicle);

                    // Redirect back to list page
                    response.sendRedirect("vehicle?action=list");
                    break;

                // Default
            default:
                    response.sendRedirect("vehicle?action=list");
            }
        }
    }
