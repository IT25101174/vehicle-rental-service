<%@ page import="com.vehiclerental.model.Vehicle" %>

<html>
<head>
    <title>Edit Vehicle</title>
</head>
<body>

<h2>Edit Vehicle</h2>

<%
    // Get the selected vehicle object from servlet
    Vehicle vehicle = (Vehicle) request.getAttribute("vehicle");
%>

<!-- This form sends updated vehicle details to VehicleServlet -->
<form action="../vehicle" method="post">

    <!-- Action tells servlet that this is an UPDATE request -->
    <input type="hidden" name="action" value="update">

    <!-- Vehicle ID must be sent to identify which record to update -->
    <input type="hidden" name="id" value="<%= vehicle.getId() %>">

    <label>Brand:</label>
    <input type="text" name="brand" value="<%= vehicle.getBrand() %>" required>
    <br><br>

    <label>Type:</label>
    <input type="text" name="type" value="<%= vehicle.getType() %>" required>
    <br><br>

    <label>Price Per Day:</label>
    <input type="number" name="pricePerDay" step="0.01"
           value="<%= vehicle.getPricePerDay() %>" required>
    <br><br>

    <label>Available:</label>
    <select name="available">
        <option value="true" <%= vehicle.isAvailable() ? "selected" : "" %>>Yes</option>
        <option value="false" <%= !vehicle.isAvailable() ? "selected" : "" %>>No</option>
    </select>

    <br><br>

    <button type="submit">Update Vehicle</button>
</form>

<br>

<!-- Link back to list page -->
<a href="../vehicle?action=list">Back to Vehicle List</a>

</body>
</html>