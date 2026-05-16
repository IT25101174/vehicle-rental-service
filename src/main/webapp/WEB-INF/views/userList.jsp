<%@ page import="java.util.ArrayList" %>
<%@ page import="com.vehiclerental.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Users - Admin View</title>
    <style>
        body { font-family: sans-serif; padding: 40px; background: #f4f4f9; }
        .container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #2c3e50; color: white; }
    </style>
</head>
<body>

<div class="container">
    <h2>System Users Directory</h2>
    <p>Admin Access Only</p>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>

        <%-- This is where the Java Magic happens! --%>
        <%
            // Unpack the delivery truck we sent from the Servlet
            ArrayList<User> users = (ArrayList<User>) request.getAttribute("userList");

            if (users != null && !users.isEmpty()) {
                // Standard Java for-loop to generate HTML rows
                for (User u : users) {
        %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getName() %></td>
            <td><%= u.getEmail() %></td>
            <% String roleColor = "admin".equalsIgnoreCase(u.getRole()) ? "#f87171" : "#4ade80"; %>
            <td><strong style="color: <%= roleColor %>;"><%= u.getRole().toUpperCase() %></strong></td>
            <td>
                <a href="user?action=edit&id=<%= u.getId() %>" style="color: blue;">Edit</a> |
                <a href="user?action=delete&id=<%= u.getId() %>" style="color: red;" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">No users found in the system.</td>
        </tr>
        <%
            }
        %>
    </table>

    <br>
    <a href="login.html">Logout</a>
</div>

</body>
</html>