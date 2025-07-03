<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="container mt-4">
    <h2>Checkout</h2>
    <form action="<%=contextPath%>/checkout" method="post">
        <div class="mb-3">
            <label>Name</label>
            <input type="text" name="name" required class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Address</label>
            <input type="text" name="address" required class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Payment Method</label>
            <select name="payment" class="form-control">
                <option value="COD">Cash on Delivery</option>
                <option value="Card">Credit Card</option>
            </select>
        </div>
        <div class="mb-3">
            <label>Voucher Code (optional)</label>
            <input type="text" name="usedVoucher" class="form-control"/>
        </div>

        <div class="mb-3">
            <strong>Total: <span style="color: #b30000;">$<c:out value="${totalPrice}" /></span></strong>
        </div>

        <button type="submit" class="btn btn-success">Place Order</button>
        <a class="btn btn-secondary" href="<%=contextPath%>/cart">Back to Cart</a>
    </form>
</div>
</body>
</html>
