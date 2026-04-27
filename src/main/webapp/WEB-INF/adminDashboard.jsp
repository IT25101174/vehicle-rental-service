<%--
  Created by IntelliJ IDEA.
  User: thami
  Date: 4/28/2026
  Time: 1:59 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: sans-serif; padding: 40px; background: #111113; color: white; }
        .container { background: #18181b; padding: 30px; border-radius: 10px; border: 1px solid #333; }
        .btn { background: #f0a500; color: black; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold; margin-right: 10px; }
        .btn:hover { background: #d69300; }
    </style>
</head>
<body>

<div class="container">
    <h1>Welcome to the Admin Dashboard</h1>
    <p>System Overview & Control Center.</p>

    <br><br>

    <a href="/register?action=listUsers" class="btn">View All Customers</a>

    <a href="/admin?action=listAdmins" class="btn">Manage Admins</a>

    <br><br><br>
    <a href="/login.html" style="color: #888;">Logout</a>
</div>

</body>
</html>