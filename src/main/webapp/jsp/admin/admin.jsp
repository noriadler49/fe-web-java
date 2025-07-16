<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            display: flex;
        }
        .sidebar {
            width: 220px;
            height: 100vh;
            background-color: #343a40;
            padding-top: 20px;
        }
        .sidebar a {
            color: white;
            padding: 10px 20px;
            display: block;
            text-decoration: none;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .content {
            flex-grow: 1;
            padding: 30px;
        }
    </style>
</head>
<body>

<div class="sidebar">
    <h4 class="text-white text-center mb-4">Admin Panel</h4>
    <a href="<%=contextPath%>/jsp/admin/admin_dish.jsp">Manage Dishes</a>
    <a href="<%=contextPath%>/jsp/admin/admin_order.jsp">Manage Orders</a>
    <a href="<%=contextPath%>/jsp/admin/admin_ingredient.jsp">Manage Ingredients</a>
    <a href="<%=contextPath%>/jsp/admin/admin_account.jsp">Manage Accounts</a>
    <a href="<%=contextPath%>/logout" class="text-danger">Logout</a>
</div>

<div class="content">
    <h2>Welcome, Admin!</h2>
    <p>Use the sidebar to manage the system.</p>
</div>

</body>
</html>
