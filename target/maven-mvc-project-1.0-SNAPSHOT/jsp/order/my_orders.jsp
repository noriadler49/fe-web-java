<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        /* Table Styling */
        .cart-table {
            border-radius: 10px;
            overflow: hidden;
            border-collapse: separate;
            border-spacing: 0;
        }

        .cart-table thead {
            background-color: #b30000;
            color: white;
        }

        .cart-table thead th {
            padding: 12px;
            text-align: center;
        }

        .cart-table tbody tr {
            transition: all 0.3s ease-in-out;
        }

        .cart-table tbody tr:hover {
            background-color: #ffe6e6;
        }

        .cart-table th,
        .cart-table td {
            text-align: center;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">My Orders</h2>

    <c:if test="${empty orders}">
        <div class="alert alert-info">You have no orders yet.</div>
    </c:if>

    <c:if test="${not empty orders}">
        <table class="table cart-table">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Total</th>
                <th>Status</th>
                <th>Voucher</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>$${order.totalPrice}</td>
                    <td>${order.status}</td>
                    <td>${order.voucherCode}</td>
                    <td>${order.createdAt}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
