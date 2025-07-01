package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.AccountDAO;
import com.mycompany.maven.mvc.project.dao.DishDAO;
import com.mycompany.maven.mvc.project.model.Dish;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DishDAO dishDAO = new DishDAO();
        AccountDAO accountDAO = new AccountDAO();

        List<Dish> featuredDishes = dishDAO.getRandomDishes(3);
        int totalUsers = accountDAO.getTotalUsers();

        request.setAttribute("featuredDishes", featuredDishes);
        request.setAttribute("totalUsers", totalUsers);

        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);

    }
}
