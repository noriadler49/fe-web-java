<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
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
    <h2 class="text-center text-danger">Register</h2>
    <form action="<%=contextPath%>/register" method="post">
        <div class="mb-3">
            <input name="username" class="form-control" placeholder="Username" required
       pattern="(?=.*[A-Z]).{8,}"
       title="Username must be at least 8 characters and contain at least one uppercase letter."
       value="${param.username != null ? param.username : ''}">
        </div>
        <div class="mb-3">
            <input name="password" type="password" class="form-control" placeholder="Password" required
       pattern="(?=.*[A-Za-z])(?=.*\d).{8,}"

       title="Password must be at least 8 characters long and contain both letters and numbers.">
        </div>
        <div class="mb-3">
            <select name="role" class="form-select">
                <option>User</option>
                <option>Staff</option>
            </select>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-danger">Register</button>
        </div>
        <div style="color:red; margin-top:10px">${error}</div>
    </form>
    <a href="<%=contextPath%>/jsp/account/login.jsp" class="text-center text-decoration-none">Already have an account? Login</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
