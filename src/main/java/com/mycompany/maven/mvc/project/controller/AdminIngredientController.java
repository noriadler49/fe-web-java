package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;
import java.util.List;

import com.mycompany.maven.mvc.project.dao.IngredientDAO;
import com.mycompany.maven.mvc.project.model.Ingredient;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/ingredients")
public class AdminIngredientController extends HttpServlet {
    private final IngredientDAO dao = new IngredientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null || action.equals("list")) {
            List<Ingredient> list = dao.getAllIngredients();
            req.setAttribute("ingredients", list);
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/admin_ingredient.jsp");
            rd.forward(req, resp);

        } else if (action.equals("new")) {
            req.setAttribute("ingredient", new Ingredient());
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/edit_ingredient.jsp");
            rd.forward(req, resp);

        } else if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("ingredientId"));
            Ingredient ing = dao.getIngredientById(id);
            req.setAttribute("ingredient", ing);
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/edit_ingredient.jsp");
            rd.forward(req, resp);

        } else if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("ingredientId"));
            dao.deleteIngredient(id);
            resp.sendRedirect(req.getContextPath() + "/admin/ingredients");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String name = req.getParameter("ingredientName");

        if (action.equals("add")) {
            Ingredient ing = new Ingredient();
            ing.setIngredientName(name);
            dao.addIngredient(ing);

        } else if (action.equals("update")) {
            int id = Integer.parseInt(req.getParameter("ingredientId"));
            Ingredient ing = new Ingredient();
            ing.setIngredientId(id);
            ing.setIngredientName(name);
            dao.updateIngredient(ing);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/ingredients");
    }
}
