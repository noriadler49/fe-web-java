<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            padding-top: 50px;
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #fff;
        }
        .form-container h2 {
            margin-bottom: 20px;
        }
        .form-container a {
            display: block;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2 class="text-center text-danger">Login</h2>
    <form action="<%=contextPath%>/login" method="post">
        <div class="mb-3">
            <input name="username" class="form-control" placeholder="Username" required>
        </div>
        <div class="mb-3">
            <input name="password" type="password" class="form-control" placeholder="Password" required>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-danger">Login</button>
        </div>
        <div style="color:red; margin-top:10px">${error}</div>
    </form>
    <a href="<%=contextPath%>/jsp/account/register.jsp" class="text-center text-decoration-none">Don't have an account? Register</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
