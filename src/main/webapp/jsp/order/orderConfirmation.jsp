<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-success">Order Placed Successfully!</h2>
    <p>Your Order ID: <strong>${order.orderId}</strong></p>
    <p>Status: <strong>${order.status}</strong></p>
    <p>Total: <strong>${order.totalPrice} VND</strong></p>

    <hr/>
    <h4>Order Details:</h4>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Dish</th>
            <th>Quantity</th>
            <th>Unit Price</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.dishName}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price} VND</td>

                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="<%=contextPath%>/menu?category=All" class="btn btn-primary">Continue Shopping</a>
</div>
</body>
</html>
