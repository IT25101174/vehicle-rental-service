package com.vehiclerental.servlet;

import com.vehiclerental.model.Booking;
import com.vehiclerental.model.Vehicle;
import com.vehiclerental.model.Payment;
import com.vehiclerental.model.CardPayment;
import com.vehiclerental.model.CashPayment;
import com.vehiclerental.service.BookingService;
import com.vehiclerental.service.VehicleService;
import com.vehiclerental.service.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private PaymentService paymentService = new PaymentService();
    private BookingService bookingService = new BookingService();
    private VehicleService vehicleService = new VehicleService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("process".equals(action)) {
            try {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("userId") == null) {
                    response.sendRedirect("login.html?error=required");
                    return;
                }

                int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                double amount = Double.parseDouble(request.getParameter("amount"));
                String paymentType = request.getParameter("paymentType");
                String date = java.time.LocalDate.now().toString();

                Payment payment;
                if ("card".equalsIgnoreCase(paymentType)) {
                    String cardNumber = request.getParameter("cardNumber");
                    // Mock secure gateway transaction reference ID
                    String transactionId = "TXN_" + System.currentTimeMillis();
                    payment = new CardPayment(0, bookingId, amount, date, "Completed", cardNumber, transactionId);
                } else {
                    String branchLocation = request.getParameter("branchLocation");
                    // Mock auto counter representative employee assignment
                    String employeeId = "EMP_" + (int)(Math.random() * 900 + 100);
                    payment = new CashPayment(0, bookingId, amount, date, "Pending", branchLocation, employeeId);
                }

                paymentService.addPayment(payment);
                response.sendRedirect("payment?action=receipt&bookingId=" + bookingId);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "checkout";
        }

        switch (action) {
            case "checkout":
                try {
                    HttpSession session = request.getSession(false);
                    if (session == null || session.getAttribute("userId") == null) {
                        response.sendRedirect("login.html?error=required");
                        return;
                    }

                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));

                    // Security check: if payment already exists for this booking, route directly to receipt!
                    Payment existing = paymentService.getPaymentByBookingId(bookingId);
                    if (existing != null) {
                        response.sendRedirect("payment?action=receipt&bookingId=" + bookingId);
                        return;
                    }

                    Booking booking = bookingService.getBookingById(bookingId);
                    if (booking == null) {
                        response.sendRedirect("index.jsp");
                        return;
                    }

                    Vehicle vehicle = vehicleService.getVehicleById(booking.getVehicleId());
                    request.setAttribute("booking", booking);
                    request.setAttribute("vehicle", vehicle);

                    request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;

            case "receipt":
                try {
                    HttpSession session = request.getSession(false);
                    if (session == null || session.getAttribute("userId") == null) {
                        response.sendRedirect("login.html?error=required");
                        return;
                    }

                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                    Payment payment = paymentService.getPaymentByBookingId(bookingId);
                    
                    if (payment == null) {
                        response.sendRedirect("index.jsp");
                        return;
                    }

                    Booking booking = bookingService.getBookingById(bookingId);
                    Vehicle vehicle = vehicleService.getVehicleById(booking.getVehicleId());

                    request.setAttribute("payment", payment);
                    request.setAttribute("booking", booking);
                    request.setAttribute("vehicle", vehicle);

                    request.getRequestDispatcher("/WEB-INF/views/receipt.jsp").forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;

            case "adminList":
                try {
                    HttpSession session = request.getSession(false);
                    if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
                        response.sendRedirect("index.jsp");
                        return;
                    }

                    List<Payment> payments = paymentService.getAllPayments();
                    request.setAttribute("payments", payments);
                    request.getRequestDispatcher("/WEB-INF/views/adminPayments.jsp").forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;

            case "confirm":
                try {
                    HttpSession session = request.getSession(false);
                    if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
                        response.sendRedirect("index.jsp");
                        return;
                    }

                    int paymentId = Integer.parseInt(request.getParameter("id"));
                    paymentService.updatePaymentStatus(paymentId, "Completed");
                    response.sendRedirect("payment?action=adminList");

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;

            case "delete":
                try {
                    HttpSession session = request.getSession(false);
                    if (session == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
                        response.sendRedirect("index.jsp");
                        return;
                    }

                    int paymentId = Integer.parseInt(request.getParameter("id"));
                    paymentService.deletePayment(paymentId);
                    response.sendRedirect("payment?action=adminList");

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;

            default:
                response.sendRedirect("index.jsp");
        }
    }
}
