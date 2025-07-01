package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;

import com.mycompany.maven.mvc.project.dao.AccountDAO;
import com.mycompany.maven.mvc.project.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private AccountDAO dao = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Lấy account chi tiết thay vì chỉ checkLogin
    Account acc = dao.getAccountByUsernameAndPassword(username, password);

    if (acc != null) {
        request.getSession().setAttribute("username", acc.getAccountUsername());
        request.getSession().setAttribute("role", acc.getAccountRole()); // set role vào session
        response.sendRedirect("home");
    } else {
        request.setAttribute("error", "Invalid login.");
        request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
    }
}

    
}

