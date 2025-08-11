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
        <input type="hidden" name="action"
               value="${dish.dishId == 0 ? 'add' : 'update'}"/>
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

        <!-- INGREDIENTS: search + native multi-select (shows max 5 rows before scroll) -->
        <!-- Ingredient selector full width -->
<div class="col-12 col-md-6">
    <label for="ingredientSearch" class="form-label">Ingredients</label>
    <input id="ingredientSearch" class="form-control mb-2" placeholder="Search ingredients...">

    <select name="ingredientIds" id="ingredientSelect" class="form-select" multiple size="5">
        <c:forEach var="ing" items="${ingredients}">
            <option value="${ing.ingredientId}"
                <c:if test="${not empty dish.ingredients}">
                    <c:forEach var="sel" items="${dish.ingredients}">
                        <c:if test="${sel.ingredientId == ing.ingredientId}">selected</c:if>
                    </c:forEach>
                </c:if>
            >${ing.ingredientName}</option>
        </c:forEach>
    </select>
    <div class="form-text">Hold Ctrl/Cmd or Shift to select multiple, or use the search above to filter.</div>
</div>

<!-- Submit button row -->
<div class="col-12 mt-3">
    <button type="submit" class="btn btn-primary">
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

<script>
document.addEventListener('DOMContentLoaded', function(){
  const search = document.getElementById('ingredientSearch');
  const select = document.getElementById('ingredientSelect');

  // simple filter: hide options whose text doesn't include query
  search.addEventListener('input', function(){
    const q = this.value.trim().toLowerCase();
    for (let i = 0; i < select.options.length; i++) {
      const opt = select.options[i];
      opt.hidden = q && !opt.text.toLowerCase().includes(q);
    }
  });
});
</script>

</body>
</html> 
