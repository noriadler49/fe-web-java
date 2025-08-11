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

            // Basic null/empty check
            if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("error", "Username and password cannot be empty.");
                request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
                return;
            }

            username = username.trim();
            password = password.trim();

            // Server-side regex validation
            if (!username.matches("^(?=.*[A-Z]).{8,}$")) {
                request.setAttribute("error", "Username must be at least 8 characters and include at least one uppercase letter.");
                request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
                return;
            }

            if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
                request.setAttribute("error", "Password must be at least 8 characters long and include both letters and numbers.");
                request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
                return;
            }

            // Proceed with authentication
            Account acc = dao.getAccountByUsernameAndPassword(username, password);

            if (acc != null) {
                request.getSession().setAttribute("username", acc.getAccountUsername());
                request.getSession().setAttribute("role", acc.getAccountRole());

                if ("Staff".equalsIgnoreCase(acc.getAccountRole())) {
                    response.sendRedirect("jsp/admin/admin.jsp");
                } else {
                    response.sendRedirect("home");
                }
            } else {
                request.setAttribute("error", "Invalid username or password.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("/jsp/account/login.jsp").forward(request, response);
            }


    }

}

