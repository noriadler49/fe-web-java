package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.DishDAO;
import com.mycompany.maven.mvc.project.model.Dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DishDetailsServlet", urlPatterns = {"/dishDetails"})
public class DishDetailsServlet extends HttpServlet {

    private DishDAO dishDAO = new DishDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Dish dish = dishDAO.getDishById(id); // 👉 bạn cần có hàm này trong DAO
                if (dish != null) {
                    request.setAttribute("dish", dish);
                    request.getRequestDispatcher("/jsp/dish/details.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("menu?category=All"); // fallback
    }
}
