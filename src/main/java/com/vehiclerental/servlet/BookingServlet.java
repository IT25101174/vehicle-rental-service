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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        String start = request.getParameter("startDate");
        String end = request.getParameter("endDate");

        if (service.isVehicleAvailable(vehicleId, start, end)) {

            int id = FileHandler.getNextId("data/bookings.txt");

            Booking booking = new Booking(id, userId, vehicleId, start, end, "active");

            service.addBooking(booking);

            response.sendRedirect("booking?action=my");

        } else {
            response.getWriter().println("Vehicle not available!");
        }
    }

    // READ bookings
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("all".equals(action)) {
            List<Booking> list = service.getAllBookings();
            request.setAttribute("bookings", list);
            request.getRequestDispatcher("WEB-INF/views/allBookings.jsp").forward(request, response);

        } else { // default → my bookings
            int userId = 2; // demo (later session use pannalam)
            List<Booking> list = service.getBookingsByUser(userId);
            request.setAttribute("bookings", list);
            request.getRequestDispatcher("WEB-INF/views/myBookings.jsp").forward(request, response);
        }
    }
}