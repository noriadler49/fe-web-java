<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
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

        .cart-table td {
            padding: 10px;
            text-align: center;
            vertical-align: middle;
        }

        .quantity-input {
            width: 60px;
            text-align: center;
            border: 1px solid #b30000;
            border-radius: 5px;
            margin-right: 8px;
        }

        .d-flex.align-items-center {
            justify-content: center;
        }

        .btn-update {
            background-color: #ff6666;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 5px 10px;
        }

        .btn-update:hover {
            background-color: #cc0000;
        }

        .btn-remove {
            background-color: #b30000;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 5px 10px;
        }

        .btn-remove:hover {
            background-color: #800000;
        }

        .total-price {
            font-size: 1.5rem;
            font-weight: bold;
            color: #b30000;
        }

        .btn-continue {
            background-color: #cccccc;
            color: black;
            padding: 10px 15px;
            border-radius: 5px;
        }

        .btn-continue:hover {
            background-color: #b3b3b3;
        }

        .btn-checkout {
            background-color: #009900;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
        }

        .btn-checkout:hover {
            background-color: #006600;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">Your Cart</h2>

    <table class="table cart-table">
        <thead>
        <tr>
            <th>Dish</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.dish.dishName}</td>
                <td>
                    <form action="updateCartQuantity" method="post" class="d-flex align-items-center">
                        <input type="hidden" name="cartItemId" value="${item.cartItemId}"/>
                        <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control quantity-input"/>
                        <button type="submit" class="btn btn-sm btn-update">Update</button>
                    </form>
                </td>
                <td>$${item.dish.dishPrice}</td>
                <td>$<c:out value="${item.quantity * item.dish.dishPrice}" /></td>
                <td>
                    <form action="removeCartItem" method="post">
                        <input type="hidden" name="cartItemId" value="${item.cartItemId}"/>
                        <button type="submit" class="btn btn-sm btn-remove">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="text-end">
        <h4 class="total-price">Total: $<c:out value="${totalPrice}" /></h4>
    </div>

    <div class="text-end mt-3">
        <a class="btn btn-continue" href="${contextPath}/menu?category=All">Continue Shopping</a>
        <a class="btn btn-checkout" href="${contextPath}/checkout">Checkout</a>
    </div>
</div>
</body>
</html>
