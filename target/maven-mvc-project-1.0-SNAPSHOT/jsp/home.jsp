<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
    Integer totalUsers = (Integer) request.getAttribute("totalUsers");
%>
<style>
        body {
        padding-top: 50px;
        background-color: #f8f9fa;
    }

    .btn-danger, .btn-outline-danger {
        margin: 0 5px;
    }

    .back-home {
        position: absolute;
        top: 20px;
        left: 20px;
    }

    .dish-card img {
        height: 235px;
        object-fit: cover;
    }

    .category-bar, .search-bar {
        margin-top: 30px;
        margin-bottom: 20px;
    }

    .category-bar a {
        margin: 0 8px;
    }

    .search-bar input {
        width: 250px;
    }

    .card-title {
        font-weight: bold;
    }
</style>
<html>
<head>
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
</head>
<body>

<!-- Navigation Bar with Dropdown Menu -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold me-3" href="<%=contextPath%>/home">Food Ordering Service</a>

        <!-- Dropdown Menu -->
        <div class="dropdown ms-auto">
            <button class="btn btn-dark dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <i class="fas fa-bars"></i>
            </button>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                <c:choose>
                    <c:when test="${not empty sessionScope.username}">
                        <!-- Show Username -->
                        <li><span class="dropdown-item-text fw-bold">Hello, ${sessionScope.username}</span></li>
                        <li><hr class="dropdown-divider"></li>

                        <!-- Role: Staff -->
                        <c:if test="${sessionScope.role == 'Staff'}">
                            <li><a class="dropdown-item" href="<%=contextPath%>/orders">Orders</a></li>
                            <li class="dropdown-submenu">
                                <a class="dropdown-item dropdown-toggle" href="#">Add</a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="<%=contextPath%>/addDish">Add Dish</a></li>
                                    <li><a class="dropdown-item" href="<%=contextPath%>/addIngredient">Add Ingredient</a></li>
                                    <li><a class="dropdown-item" href="<%=contextPath%>/addVoucher">Add Voucher</a></li>
                                </ul>
                            </li>
                        </c:if>

                        <!-- Role: User -->
                        <c:if test="${sessionScope.role == 'User'}">
                            <li><a class="dropdown-item" href="<%=contextPath%>/cart">Cart</a></li>
                            <li><a class="dropdown-item" href="<%=contextPath%>/myOrders">My Orders</a></li>
                        </c:if>

                        <!-- Common Items -->
                        <li><a class="dropdown-item" href="<%=contextPath%>/menu?category=All">Menu</a></li>
                        <li><a class="dropdown-item text-danger" href="<%=contextPath%>/logout">Logout</a></li>
                    </c:when>

                    <c:otherwise>
                        <!-- Not logged in -->
                        <li><a class="dropdown-item" href="<%=contextPath%>/jsp/account/login.jsp">Login</a></li>
                        <li><a class="dropdown-item" href="<%=contextPath%>/jsp/account/register.jsp">Register</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=contextPath%>/menu?category=All">Menu</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
<div class="container mt-4 text-center">
    <h1 class="display-4 text-danger">Welcome to Our Food Ordering Service!</h1>
    <p class="lead">Order your favorite dishes and get them delivered to your doorstep!</p>

    <div class="row mt-4">
        <h2 class="text-center mb-4">Featured Dishes</h2>

        <c:forEach var="dish" items="${featuredDishes}">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <c:choose>
    <c:when test="${fn:startsWith(dish.dishImageUrl, 'http')}">
        <img src="${dish.dishImageUrl}" class="card-img-top" alt="${dish.dishName}">
    </c:when>
    <c:otherwise>
        <img src="${pageContext.request.contextPath}/images/${dish.dishImageUrl}" class="card-img-top" alt="${dish.dishName}">
    </c:otherwise>
</c:choose>


                    <div class="card-body">
                        <h5 class="card-title">${dish.dishName}</h5>
                        <p class="card-text">${dish.dishDescription}</p>
                        <p class="card-text fw-bold">${dish.dishPrice}VND</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
 <!-- Nút Menu -->
    <div class="text-center mt-3">
        <a href="<%=contextPath%>/menu?category=All" class="btn btn-primary btn-lg">
            <i class="fas fa-utensils"></i> View Full Menu
        </a>
    </div>

    <div class="text-center mt-5">
        <h3>There are <strong>${totalUsers}</strong> users using our service</h3>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>