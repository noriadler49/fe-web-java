<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${ingredient.ingredientId == 0}">Add New Ingredient</c:when>
            <c:otherwise>Edit Ingredient</c:otherwise>
        </c:choose>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-4">
<div class="container">
    <h2>
        <c:choose>
            <c:when test="${ingredient.ingredientId == 0}">Add New Ingredient</c:when>
            <c:otherwise>Edit Ingredient</c:otherwise>
        </c:choose>
    </h2>

    <form action="<%= contextPath %>/admin/ingredients" method="post" class="row g-3">
        <input type="hidden" name="action"
               value="${ingredient.ingredientId == 0 ? 'add' : 'update'}"/>
        <c:if test="${ingredient.ingredientId != 0}">
            <input type="hidden" name="ingredientId" value="${ingredient.ingredientId}"/>
        </c:if>

        <div class="col-md-6">
            <label for="ingredientName" class="form-label">Ingredient Name</label>
            <input id="ingredientName" name="ingredientName" value="${ingredient.ingredientName}"
                   class="form-control" placeholder="Enter ingredient name" required/>
        </div>

        <div class="col-12 mt-3">
            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${ingredient.ingredientId == 0}">Create</c:when>
                    <c:otherwise>Save Changes</c:otherwise>
                </c:choose>
            </button>
           <a href="<%= contextPath %>/admin/ingredients" class="btn btn-secondary ms-2">← Back to List</a>

        </div>
    </form>
</div>

<script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
