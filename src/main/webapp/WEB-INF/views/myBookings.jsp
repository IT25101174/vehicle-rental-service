<%@ page import="java.util.List" %>
<%@ page import="com.vehiclerental.model.Booking" %>

<style>
body {
    background: #09090b;
    color: #f4f0ea;
    font-family: 'Outfit', sans-serif;
    padding: 40px;
}

.card {
    background: #111113;
    border: 1px solid rgba(255,255,255,0.08);
    padding: 20px;
    margin-bottom: 15px;
    border-radius: 8px;
}

h2 {
    font-family: 'Syne', sans-serif;
    margin-bottom: 20px;
}

span {
    color: #f0a500;
}
</style>

<h2>My <span>Bookings</span></h2>

<%
List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");

for (Booking b : bookings) {
%>

<div class="card">
    <p><b>ID:</b> <%= b.getId() %></p>
    <p><b>Vehicle:</b> <%= b.getVehicleId() %></p>
    <p><b>From:</b> <%= b.getStartDate() %></p>
    <p><b>To:</b> <%= b.getEndDate() %></p>
    <p><b>Status:</b> <span><%= b.getStatus() %></span></p>
</div>

<%
}
%>