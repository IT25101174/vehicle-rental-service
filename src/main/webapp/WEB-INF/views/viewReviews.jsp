<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Reviews</title>
</head>
<body>

    <h2>Vehicle Reviews</h2>

    <a href="/addReview.html">Write a Review</a>
    <br><br>

    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>User ID</th>
            <th>Vehicle ID</th>
            <th>Rating</th>
            <th>Comment</th>
            <th>Type</th>
        </tr>

        <c:forEach var="review" items="${reviews}">
            <tr>
                <td>${review.id}</td>
                <td>${review.userId}</td>
                <td>${review.vehicleId}</td>
                <td>${review.rating} / 5</td>
                <td>${review.comment}</td>
                <td>${review.type}</td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>