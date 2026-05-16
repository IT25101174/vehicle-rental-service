package com.vehiclerental.servlet;

import com.vehiclerental.model.Review;
import com.vehiclerental.service.ReviewService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
        import java.io.IOException;
import java.util.List;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    ReviewService reviewService = new ReviewService();

    // doGet = when someone visits the page to VIEW reviews
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vehicleIdStr = request.getParameter("vehicleId");
        List<Review> reviews;
        if (vehicleIdStr != null) {
            reviews = reviewService.getReviewsByVehicle(Integer.parseInt(vehicleIdStr));
        } else {
            reviews = reviewService.getAllReviews();
        }
        request.setAttribute("reviews", reviews);
        try {
            request.getRequestDispatcher("/WEB-INF/views/viewReviews.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // doPost = when someone SUBMITS the form to add a review
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            reviewService.deleteReview(id);
        } else {
            // Default: add a new review
            int userId = Integer.parseInt(request.getParameter("userId"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");
            String type = request.getParameter("type");
            reviewService.addReview(userId, vehicleId, rating, comment, type);
        }

        response.sendRedirect("/review");
    }
}