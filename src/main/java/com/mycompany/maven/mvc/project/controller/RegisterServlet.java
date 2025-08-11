package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;

import com.mycompany.maven.mvc.project.dao.AccountDAO;
import com.mycompany.maven.mvc.project.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private AccountDAO dao = new AccountDAO();

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String role = request.getParameter("role");

    if (username == null || password == null ||
        username.trim().isEmpty() || password.trim().isEmpty()) {
        request.setAttribute("error", "Username and password cannot be empty.");
        request.getRequestDispatcher("/jsp/account/register.jsp").forward(request, response);
        return;
    }

    username = username.trim();
    password = password.trim();

    // Validate username pattern
    if (!username.matches("^(?=.*[A-Z]).{8,}$")) {
        request.setAttribute("error", "Username must be at least 8 characters and include at least one uppercase letter.");
        request.getRequestDispatcher("/jsp/account/register.jsp").forward(request, response);
        return;
    }

    // Validate password pattern
    if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
        request.setAttribute("error", "Password must be at least 8 characters long and include both letters and numbers.");
        request.getRequestDispatcher("/jsp/account/register.jsp").forward(request, response);
        return;
    }

    Account acc = new Account(username, password, role);

    boolean success = dao.register(acc);

    if (success) {
        response.sendRedirect("login"); // Redirect to login page after successful registration
    } else {
        request.setAttribute("error", "Register failed. Username might already exist.");
        request.getRequestDispatcher("/jsp/account/register.jsp").forward(request, response);
    }
}

}

