<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.maven.mvc.project.model.Dish" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String contextPath = request.getContextPath();
    List<Dish> dishes = (List<Dish>) request.getAttribute("dishes");
    List<String> categories = (List<String>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Dishes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        .actions form, .actions a { display:inline-block; margin-right:5px; }
        img.thumb { width:60px; height:60px; object-fit:cover; }
    </style>
</head>

<body class="p-4">
<div class="container">
    <h2 class="mb-4">Manage Dishes</h2>

    <!-- ADD NEW DISH BUTTON -->
    <a href="<%=contextPath%>/admin/dishes?action=new" class="btn btn-success mb-3">
        + Add New Dish
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Image</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${dishes}">
            <tr>
                <td>${d.dishId}</td>
                <td>${d.dishName}</td>
                <td>
                    <!-- <c:if test="${not empty d.dishImageUrl}">
                        <img src="${d.dishImageUrl}" class="thumb"/>
                    </c:if> -->
                    <c:choose>
                        <c:when test="${fn:startsWith(d.dishImageUrl, 'http')}">
                            <img src="${d.dishImageUrl}" alt="Dish Image" class="thumb"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/${d.dishImageUrl}" alt="Dish Image" class="thumb"/>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>${d.dishDescription}</td>
                <td>${d.dishPrice}</td>
                <td>${d.categoryName}</td>
                <td class="actions">
                    <!-- Edit -->
                    <a href="<%=contextPath%>/admin/dishes?action=edit&dishId=${d.dishId}"
                       class="btn btn-sm btn-warning">Edit</a>

                    <!-- Delete -->
                    <form action="<%=contextPath%>/admin/dishes" method="get"
                          onsubmit="return confirm('Delete this dish?');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="dishId" value="${d.dishId}"/>
                        <button class="btn btn-sm btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
