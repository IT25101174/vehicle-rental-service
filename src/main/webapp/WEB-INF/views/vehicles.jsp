<%@ page import="java.util.List" %>
<%@ page import="com.vehiclerental.model.Vehicle" %>

<html>
<head>
    <title>Vehicle List</title>
</head>
<body>

<h2>Vehicle List</h2>

<!-- Link to Add Vehicle Page -->
<a href="../addVehicle.html">Add New Vehicle</a>

<br><br>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Brand</th>
        <th>Type</th>
        <th>Price Per Day</th>
        <th>Available</th>
        <th>Actions</th>
    </tr>

    <%
        // Get vehicle list from servlet
        List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");

        if (vehicles != null && !vehicles.isEmpty()) {
            for (Vehicle v : vehicles) {
    %>

    <tr>
        <td><%= v.getId() %></td>
        <td><%= v.getBrand() %></td>
        <td><%= v.getType() %></td>
        <td><%= v.getPricePerDay() %></td>
        <td><%= v.isAvailable() %></td>

        <td>
            <!-- Edit Link -->
            <a href="../vehicle?action=edit&id=<%= v.getId() %>">Edit</a>

            |

            <!-- Delete Link -->
            <a href="../vehicle?action=delete&id=<%= v.getId() %>"
               onclick="return confirm('Are you sure you want to delete this vehicle?');">
                Delete
            </a>
        </td>
    </tr>

    <%
            }
        } else {
    %>

    <tr>
        <td colspan="6">No vehicles found!</td>
    </tr>

    <%
        }
    %>

</table>

</body>
</html>