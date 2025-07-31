package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.AccountDAO;
import com.mycompany.maven.mvc.project.model.Account;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/accounts")
public class AdminAccountController extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            List<Account> accounts = accountDAO.getAllAccounts();
            request.setAttribute("accounts", accounts);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin_account.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("edit")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            Account account = accountDAO.getAccountById(accountId);
            request.setAttribute("account", account);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/edit_account.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("delete")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            accountDAO.deleteAccountById(accountId);
            response.sendRedirect(request.getContextPath() + "/admin/accounts");
        } else if (action.equals("toggleRole")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            accountDAO.toggleAccountRole(accountId);
            response.sendRedirect(request.getContextPath() + "/admin/accounts");
        } else if (action.equals("update")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Account account = new Account();
            account.setAccountId(accountId);
            account.setAccountUsername(username);
            account.setAccountPassword(password);

            accountDAO.updateAccount(account);
            response.sendRedirect(request.getContextPath() + "/admin/accounts");
        }
    }
}


