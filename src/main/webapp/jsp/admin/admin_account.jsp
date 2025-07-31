<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.maven.mvc.project.model.Account" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Accounts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-4">
    <div class="container">
        <h2 class="mb-4">Manage Accounts</h2>

        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="acc" items="${accounts}">
                <tr>
                    <td>${acc.accountId}</td>
                    <td>${acc.accountUsername}</td>
                    <td>${acc.accountPassword}</td>
                    <td>${acc.accountRole}</td>
                    <td class="actions">
                        <div class="d-flex flex-wrap gap-1">
                            <form action="<%= contextPath %>/admin/accounts" method="post">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="accountId" value="${acc.accountId}" />
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>

                            <a href="<%= contextPath %>/admin/accounts?action=edit&accountId=${acc.accountId}" 
                            class="btn btn-sm btn-warning">Edit</a>

                            <form action="<%= contextPath %>/admin/accounts" method="post">
                                <input type="hidden" name="action" value="toggleRole" />
                                <input type="hidden" name="accountId" value="${acc.accountId}" />
                                <button type="submit" class="btn btn-sm btn-secondary">Toggle Role</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
