<%@ page import="java.util.List" %>
<%@ page import="com.vehiclerental.model.Booking" %>

<style>
body {
    background: #09090b;
    color: #f4f0ea;
    font-family: 'Outfit', sans-serif;
    padding: 40px;
}

table {
    width: 100%;
    border-collapse: collapse;
    background: #111113;
}

th, td {
    padding: 12px;
    border-bottom: 1px solid rgba(255,255,255,0.08);
    text-align: left;
}

th {
    color: #f0a500;
}

h2 {
    font-family: 'Syne', sans-serif;
    margin-bottom: 20px;
}
</style>

<h2>All Bookings (Admin)</h2>

<table>
<tr>
    <th>ID</th>
    <th>User</th>
    <th>Vehicle</th>
    <th>Start</th>
    <th>End</th>
    <th>Status</th>
</tr>

<%
List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");

for (Booking b : bookings) {
%>

<tr>
    <td><%= b.getId() %></td>
    <td><%= b.getUserId() %></td>
    <td><%= b.getVehicleId() %></td>
    <td><%= b.getStartDate() %></td>
    <td><%= b.getEndDate() %></td>
    <td style="color:#f0a500;"><%= b.getStatus() %></td>
</tr>

<%
}
%>
</table>