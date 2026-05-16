<%@ page import="com.vehiclerental.model.User" %>

<%
    User user = (User) request.getAttribute("user");
%>

<style>
    body {
        background: #09090b;
        color: #f4f0ea;
        font-family: 'Outfit', sans-serif;
        padding: 40px;
    }

    form {
        background: #111113;
        padding: 20px;
        width: 400px;
        border-radius: 8px;
    }

    input {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        background: #1a1a1d;
        border: none;
        color: white;
    }

    button {
        background: #f0a500;
        border: none;
        padding: 10px;
        width: 100%;
        cursor: pointer;
        font-weight: bold;
    }
</style>

<h2>Edit User</h2>

<form action="user" method="post">

    <input type="hidden" name="action" value="update">

    <input type="hidden" name="id" value="<%= user.getId() %>">

    <label>Name</label>
    <input type="text" name="name" value="<%= user.getName() %>" required>

    <label>Email</label>
    <input type="email" name="email" value="<%= user.getEmail() %>" required>

    <label>Password</label>
    <input type="text" name="password" value="<%= user.getPassword() %>" required>

    <button type="submit">Update User</button>
</form>