<%@ page import="java.util.List" %>
<%@ page import="com.vehiclerental.model.User" %>

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

    a {
        color: #f0a500;
        text-decoration: none;
    }
</style>

<h2>User List (Admin)</h2>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Action</th>
    </tr>

    <%
        List<User> users = (List<User>) request.getAttribute("users");

        if (users != null) {
            for (User u : users) {
    %>

    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getPassword() %></td>
        <td>
            <a href="user?action=edit&id=<%= u.getId() %>">Edit</a>
        </td>
    </tr>

    <%
            }
        }
    %>

</table>