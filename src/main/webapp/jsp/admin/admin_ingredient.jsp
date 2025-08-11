<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.maven.mvc.project.model.Ingredient" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
    List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Ingredients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        .actions form, .actions a { display:inline-block; margin-right:5px; }
    </style>
</head>

<body class="p-4">
<div class="container">
    <h2 class="mb-4">Manage Ingredients</h2>

    <!-- ADD NEW INGREDIENT BUTTON -->
    <a href="<%=contextPath%>/admin/ingredients?action=new" class="btn btn-success mb-3">
        + Add New Ingredient
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ing" items="${ingredients}">
            <tr>
                <td>${ing.ingredientId}</td>
                <td>${ing.ingredientName}</td>
                <td class="actions">
                    <!-- Edit -->
                    <a href="<%=contextPath%>/admin/ingredients?action=edit&ingredientId=${ing.ingredientId}"
                       class="btn btn-sm btn-warning">Edit</a>

                    <!-- Delete -->
                    <form action="<%= contextPath %>/admin/ingredients" method="get"
                          onsubmit="return confirm('Delete this ingredient?');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="ingredientId" value="${ing.ingredientId}"/>
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
