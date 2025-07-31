<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title><c:choose>
        <c:when test="${dish.dishId == 0}">Add New Dish</c:when>
        <c:otherwise>Edit Dish</c:otherwise>
    </c:choose></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body class="p-4">
<div class="container">
    <h2>
        <c:choose>
            <c:when test="${dish.dishId == 0}">Add New Dish</c:when>
            <c:otherwise>Edit Dish</c:otherwise>
        </c:choose>
    </h2>

    <form action="<%= contextPath %>/admin/dishes" method="post" class="row g-2">
        <!-- action: add if new, update otherwise -->
        <input type="hidden" name="action"
               value="${dish.dishId == 0 ? 'add' : 'update'}"/>

        <!-- only include dishId when updating -->
        <c:if test="${dish.dishId != 0}">
            <input type="hidden" name="dishId" value="${dish.dishId}"/>
        </c:if>

        <div class="col-md-3">
            <input name="name" value="${dish.dishName}"
                   class="form-control" placeholder="Dish Name" required/>
        </div>
        <div class="col-md-3">
            <input name="image" value="${dish.dishImageUrl}"
                   class="form-control" placeholder="Image URL"/>
        </div>
        <div class="col-md-3">
            <input name="description" value="${dish.dishDescription}"
                   class="form-control" placeholder="Description"/>
        </div>
        <div class="col-md-1">
            <input name="price" type="number" step="0.01"
                   value="${dish.dishPrice}"
                   class="form-control" placeholder="Price" required/>
        </div>
        <div class="col-md-2">
            <select name="categoryName" class="form-select" required>
                <option value="" disabled ${dish.categoryName == null ? 'selected' : ''}>-- Category --</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c}"
                        ${c == dish.categoryName ? 'selected' : ''}>${c}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-1">
            <button type="submit" class="btn btn-primary w-100">
                <c:choose>
                    <c:when test="${dish.dishId == 0}">Create</c:when>
                    <c:otherwise>Save Changes</c:otherwise>
                </c:choose>
            </button>
        </div>
    </form>

    <a href="<%= contextPath %>/admin/dishes"
       class="btn btn-secondary mt-3">← Back to List</a>
</div>
<script src="<%= contextPath %>/js/bootstrap.bundle.min.js"></script>
</body>
</html>
