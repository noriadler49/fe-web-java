<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Menu</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">

</head>
<body>

<!-- Back to Home Button -->
<a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary back-home">
    <i class="fas fa-arrow-left"></i> Home
</a>

<div class="container text-center">

    <h2 class="text-danger mb-4">Our Menu</h2>

    <!-- Category Buttons -->
    <div class="category-bar">
        <a href="${pageContext.request.contextPath}/menu?category=All&search=${searchString}"
           class="btn ${selectedCategory eq 'All' ? 'btn-danger' : 'btn-outline-danger'}">All</a>

        <a href="${pageContext.request.contextPath}/menu?category=Food&search=${searchString}"
           class="btn ${selectedCategory eq 'Food' ? 'btn-danger' : 'btn-outline-danger'}">Food</a>

        <a href="${pageContext.request.contextPath}/menu?category=Drink&search=${searchString}"
           class="btn ${selectedCategory eq 'Drink' ? 'btn-danger' : 'btn-outline-danger'}">Drink</a>
    </div>

    <!-- Search Form -->
    <form method="get" action="${pageContext.request.contextPath}/menu" class="search-bar d-flex justify-content-center align-items-center">
        <input type="hidden" name="category" value="${selectedCategory}">
        <input type="text" name="search" value="${searchString}" class="form-control me-2" placeholder="Search dishes...">
        <button type="submit" class="btn btn-danger">Search</button>
    </form>

    <!-- Dish List -->
    <div class="row mt-4">
        <c:forEach var="item" items="${dishes}">
            <div class="col-md-4 mb-4">
                <div class="card shadow-sm h-100 dish-card">
                    <img src="${item.dishImageUrl}" class="card-img-top" alt="${item.dishName}">
                    <div class="card-body text-center">
                        <h5 class="card-title">${item.dishName}</h5>
                        <p class="text-muted">Category: ${item.categoryName}</p>
                        <p class="card-text text-success fw-bold">${item.dishPrice} VND</p>

                        <a href="<%=contextPath%>/dishDetails?id=${item.dishId}" class="btn btn-primary">View Dish</a>

                        <c:choose>
                            <c:when test="${sessionScope.role == 'Staff'}">
                                <div class="mt-2 d-flex justify-content-center">
                                    <a href="editDish?id=${item.dishId}" class="btn btn-warning me-2">Modify</a>
                                    <form action="deleteDish" method="post" onsubmit="return confirm('Are you sure?');">
                                        <input type="hidden" name="dishId" value="${item.dishId}">
                                        <button type="submit" class="btn btn-danger">Remove</button>
                                    </form>
                                </div>
                            </c:when>

                            <c:when test="${sessionScope.role == 'User'}">
                                <form action="<%=contextPath%>/cart" method="post">
                                    <input type="hidden" name="action" value="addToCart"/>
                                    <input type="hidden" name="dishId" value="${item.dishId}" />
                                    <input type="hidden" name="quantity" value="1" />
                                    <button type="submit" class="btn btn-success">Add to Cart</button>
                                </form>

                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
