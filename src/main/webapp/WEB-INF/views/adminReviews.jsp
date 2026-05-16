<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Reviews</title>
</head>
<body>

    <h2>All Reviews — Admin Panel</h2>

    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>User ID</th>
            <th>Vehicle ID</th>
            <th>Rating</th>
            <th>Comment</th>
            <th>Type</th>
            <th>Action</th>
        </tr>

        <c:forEach var="review" items="${reviews}">
            <tr>
                <td>${review.id}</td>
                <td>${review.userId}</td>
                <td>${review.vehicleId}</td>
                <td>${review.rating} / 5</td>
                <td>${review.comment}</td>
                <td>${review.type}</td>
                <td>
                    <form action="/review" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${review.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>