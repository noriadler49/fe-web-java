package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.DishDAO;
import com.mycompany.maven.mvc.project.model.Dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MenuServlet", urlPatterns = "/menu")
public class MenuServlet extends HttpServlet {
    private DishDAO dishDAO = new DishDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String search = request.getParameter("search");

        if (category == null || category.isEmpty()) {
            category = "All";
        }

        List<Dish> dishes = dishDAO.getAllDishes(category, search);
        request.setAttribute("dishes", dishes);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("searchString", search);

        request.getRequestDispatcher("/jsp/menu/menu.jsp").forward(request, response);


    }
}
