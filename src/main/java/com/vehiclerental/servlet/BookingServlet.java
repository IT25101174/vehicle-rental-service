package com.vehiclerental.servlet;

import com.vehiclerental.model.Booking;
import com.vehiclerental.service.BookingService;
import com.vehiclerental.FileHandler;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private BookingService service = new BookingService();

    // CREATE booking
    // Added ServletException for VS Code
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get current user from session
        HttpSession session = request.getSession();
        Integer sessionUserId = (Integer) session.getAttribute("userId");
        
        if (sessionUserId == null) {
            response.sendRedirect("login.html");
            return;
        }

        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");

        if (service.isVehicleAvailable(vehicleId, start, end)) {
            Booking booking = new Booking(0, sessionUserId, vehicleId, start, end, "active");
            service.addBooking(booking);
            response.sendRedirect("booking?action=my");

        } else {
            response.getWriter().println("Vehicle not available!");
        }
    }

    // READ bookings
    // Added ServletException for VS Code
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
 
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.deleteBooking(id);
            response.sendRedirect("booking?action=my");
            return;
        }
        
        if ("all".equals(action)) {
            List<Booking> list = service.getAllBookings();
            request.setAttribute("bookings", list);
            request.getRequestDispatcher("WEB-INF/views/allBookings.jsp").forward(request, response);

        } else { // default → my bookings
            HttpSession session = request.getSession();
            Integer sessionUserId = (Integer) session.getAttribute("userId");
            
            if (sessionUserId == null) {
                response.sendRedirect("login.html");
                return;
            }
            
            List<Booking> list = service.getBookingsByUser(sessionUserId);
            request.setAttribute("bookings", list);
            request.getRequestDispatcher("WEB-INF/views/myBookings.jsp").forward(request, response);
        }
    }
}