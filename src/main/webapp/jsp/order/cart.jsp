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
                    <form action="<%=contextPath%>/cart" method="post" class="d-flex align-items-center">
                            <input type="hidden" name="action" value="updateQuantity"/>
                            <input type="hidden" name="cartItemId" value="${item.cartItemId}"/>
                            <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control quantity-input"/>
                            <button type="submit" class="btn btn-sm btn-update">Update</button>
                        </form>
                    </td>
                    <td>$${item.dish.dishPrice}</td>
                    <td>$<c:out value="${item.quantity * item.dish.dishPrice}" /></td>
                    <td>
                        <form action="<%=contextPath%>/cart" method="post">
                            <input type="hidden" name="action" value="removeItem"/>
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
            <a class="btn btn-continue" href="<%=contextPath%>/menu?category=All">Continue Shopping</a>
            <a class="btn btn-checkout" href="<%=contextPath%>/checkout">Checkout</a>
        </div>
    </div>
</body>
</html>
