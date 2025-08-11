<%@ page import="com.mycompany.maven.mvc.project.model.Account" %>
<%
    String contextPath = request.getContextPath();
    Account account = (Account) request.getAttribute("account");
%>

<form method="post" action="<%=contextPath%>/admin/accounts" 
      style="max-width: 400px; margin: 30px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px; background: #f9f9f9; font-family: Arial, sans-serif;">
    
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="accountId" value="<%= account.getAccountId() %>"/>

    <div style="margin-bottom: 15px;">
        <label for="username" style="display: block; font-weight: bold; margin-bottom: 5px;">Username:</label>
        <input type="text" id="username" name="username" 
               value="<%= account.getAccountUsername() %>" 
               style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 5px;" />
    </div>

    <div style="margin-bottom: 15px;">
        <label for="password" style="display: block; font-weight: bold; margin-bottom: 5px;">Password:</label>
        <input type="text" id="password" name="password" 
               value="<%= account.getAccountPassword() %>" 
               style="width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 5px;" />
    </div>

    <button type="submit" 
            style="width: 100%; padding: 10px; background-color: #4CAF50; color: white; font-weight: bold; border: none; border-radius: 5px; cursor: pointer;">
        Save
    </button>
</form>
