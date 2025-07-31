<%@ page import="com.mycompany.maven.mvc.project.model.Account" %>
<%
    String contextPath = request.getContextPath();
    Account account = (Account) request.getAttribute("account");
%>
<form method="post" action="<%=contextPath%>/admin/accounts">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="accountId" value="<%= account.getAccountId() %>"/>
    Username: <input type="text" name="username" value="<%= account.getAccountUsername() %>"/><br/>
    Password: <input type="text" name="password" value="<%= account.getAccountPassword() %>"/><br/>
    
    <button type="submit">Save</button>
</form>
