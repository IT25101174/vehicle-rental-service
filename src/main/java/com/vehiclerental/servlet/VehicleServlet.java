package com.vehiclerental.servlet;

import com.vehiclerental.model.Vehicle; //create vehicle objects
import com.vehiclerental.model.VehicleDetails;
import com.vehiclerental.service.VehicleService; //call service methods
import com.vehiclerental.service.VehicleDetailsService;

import jakarta.servlet.ServletException; //handle servlet errors
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest; //get the browser data
import jakarta.servlet.http.HttpServletResponse; //send to the browser data

import java.io.IOException; //handle IO errors
import java.util.List; //store vehicle list

    @WebServlet("/vehicle")
    public class VehicleServlet extends HttpServlet {

        private VehicleService vehicleService;
        private VehicleDetailsService detailsService;

        @Override
        public void init() {
            vehicleService = new VehicleService();
            detailsService = new VehicleDetailsService();
        }

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
                    List<Vehicle> vehiclesList = vehicleService.getAllVehicles();
                    request.setAttribute("vehicles", vehiclesList);
 
                    // Detect role and forward to the correct view
                    String userRole = (String) request.getSession().getAttribute("role");
                    if ("admin".equalsIgnoreCase(userRole)) {
                        request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(request, response);
                    }
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
                    List<Vehicle> searchResults = vehicleService.searchVehicles(keyword);
                    request.setAttribute("vehicles", searchResults);
 
                    // Role detection for search results too
                    String sRole = (String) request.getSession().getAttribute("role");
                    if ("admin".equalsIgnoreCase(sRole)) {
                        request.getRequestDispatcher("/WEB-INF/views/vehicles.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(request, response);
                    }
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
                    
                    String fuelType = request.getParameter("fuelType");
                    int seatingCapacity = 0;
                    try { seatingCapacity = Integer.parseInt(request.getParameter("seatingCapacity")); } catch(Exception e){}
                    boolean hasAc = Boolean.parseBoolean(request.getParameter("hasAc"));
                    boolean hasGear = Boolean.parseBoolean(request.getParameter("hasGear"));
                    
                    VehicleDetails details = new VehicleDetails(newVehicle.getId(), fuelType, seatingCapacity, hasAc, hasGear);
                    detailsService.saveDetails(details);

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
                    
                    String uFuelType = request.getParameter("fuelType");
                    int uSeatingCapacity = 0;
                    try { uSeatingCapacity = Integer.parseInt(request.getParameter("seatingCapacity")); } catch(Exception e){}
                    boolean uHasAc = Boolean.parseBoolean(request.getParameter("hasAc"));
                    boolean uHasGear = Boolean.parseBoolean(request.getParameter("hasGear"));

                    VehicleDetails uDetails = new VehicleDetails(id, uFuelType, uSeatingCapacity, uHasAc, uHasGear);
                    detailsService.saveDetails(uDetails);

                    // Redirect back to list page
                    response.sendRedirect("vehicle?action=list");
                    break;

                // Default
            default:
                    response.sendRedirect("vehicle?action=list");
            }
        }
    }
