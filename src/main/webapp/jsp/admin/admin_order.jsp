<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Order Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
    <h2>Order Management</h2>

    <form method="get" class="mb-3">
        <select name="status" onchange="this.form.submit()" class="form-select w-25">
            <option value="All" ${selectedStatus == 'All' ? 'selected' : ''}>All</option>
            <option value="Pending" ${selectedStatus == 'Pending' ? 'selected' : ''}>Pending</option>
            <option value="Completed" ${selectedStatus == 'Completed' ? 'selected' : ''}>Completed</option>
            <option value="Canceled" ${selectedStatus == 'Canceled' ? 'selected' : ''}>Canceled</option>
        </select>
    </form>

    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Status</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.accountId}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
