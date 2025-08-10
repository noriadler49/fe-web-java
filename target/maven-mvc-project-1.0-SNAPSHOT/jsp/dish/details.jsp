<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dish Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        .details-container {
            display: flex;
            justify-content: flex-start;
            align-items: flex-start;
            gap: 50px;
            max-width: 1000px;
            margin: auto;
            margin-top: 30px;
        }

        .dish-image {
            width: 55%;
            text-align: left;
        }

        .dish-image img {
            width: 100%;
            border-radius: 10px;
            box-shadow: 3px 3px 15px rgba(0, 0, 0, 0.3);
        }

        .dish-info {
            width: 40%;
        }

        .ingredients-list, .dish-description {
            border: 2px solid;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
        }

        .dish-price {
            font-size: 20px;
            font-weight: bold;
            color: #000000;
            margin-bottom: 20px;
        }

        .actions-container {
            display: flex;
            justify-content: space-between;
            margin: 40px auto;
            max-width: 1000px;
        }

        .btn-group {
            display: flex;
            gap: 10px;
        }
    </style>
</head>
<body>

<div class="details-container">
    <!-- Left: Dish Image -->
    <div class="dish-image">
        <h2>${dish.dishName}</h2>
        <c:choose>
    <c:when test="${fn:startsWith(dish.dishImageUrl, 'http')}">
        <img src="${dish.dishImageUrl}" alt="Dish Image">
    </c:when>
    <c:otherwise>
        <img src="${pageContext.request.contextPath}/images/${dish.dishImageUrl}" alt="Dish Image">
    </c:otherwise>
</c:choose>

    </div>


    <!-- Right: Info -->
    <div class="dish-info">
        <h3>Ingredients:</h3>
        <div class="ingredients-list">
            <ul>
                <c:forEach var="ing" items="${dish.ingredients}">
                    <li>${ing.ingredientName}</li>
                </c:forEach>
            </ul>
        </div>

        <h4>Description:</h4>
        <div class="dish-description">
            <p>${dish.dishDescription}</p>
        </div>

        <div class="dish-price">
            Price: ${dish.dishPrice} VND
        </div>
    </div>
</div>

<!-- Bottom Buttons -->
<div class="actions-container">
    <a class="btn btn-info" href="<%=contextPath%>/menu?category=All">Back to menu</a>

    <div class="btn-group">
        <c:choose>
            <c:when test="${sessionScope.role == 'User'}">
                <form action="<%=contextPath%>/cart" method="post">
                    <input type="hidden" name="action" value="addToCart"/>
                    <input type="hidden" name="dishId" value="${item.dishId}" />
                    <input type="hidden" name="quantity" value="1" />
                    <button type="submit" class="btn btn-success">Add to Cart</button>
                </form>

            </c:when>

            <c:when test="${sessionScope.role == 'Staff'}">
                <a href="${contextPath}/editDish?id=${dish.dishId}" class="btn btn-warning">Modify</a>
                <form action="${contextPath}/deleteDish" method="post" onsubmit="return confirm('Are you sure you want to delete this dish?');">
                    <input type="hidden" name="dishId" value="${dish.dishId}" />
                    <button type="submit" class="btn btn-danger">Remove</button>
                </form>
            </c:when>

            <c:otherwise>
                <a href="<%=contextPath%>/login" class="btn btn-warning">Login to Add to Cart</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
