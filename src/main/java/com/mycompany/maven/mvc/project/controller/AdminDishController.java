package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.maven.mvc.project.dao.DishDAO;
import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.model.Ingredient;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dishes")
public class AdminDishController extends HttpServlet {
    private final DishDAO dao = new DishDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.equals("list")) {
            List<Dish> list = dao.getAllDishes("All", null);
            List<String> cats = dao.getAllCategoryNames();
            req.setAttribute("dishes", list);
            req.setAttribute("categories", cats);
            RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/admin_dish.jsp");
            rd.forward(req, resp);

        } else if (action.equals("new")) {
        req.setAttribute("dish", new Dish());
        req.setAttribute("categories", dao.getAllCategoryNames());
        req.setAttribute("ingredients", dao.getAllIngredients()); 
        RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/edit_dish.jsp");
        rd.forward(req, resp);
        
        } else if (action.equals("edit")) {
        int id = Integer.parseInt(req.getParameter("dishId"));
        Dish d = dao.getDishById(id);
        req.setAttribute("dish", d);
        req.setAttribute("categories", dao.getAllCategoryNames());
        req.setAttribute("ingredients", dao.getAllIngredients());
        RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/edit_dish.jsp");
        rd.forward(req, resp);

        } else if (action.equals("delete")) {
    int id = Integer.parseInt(req.getParameter("dishId"));
    boolean deleted = dao.deleteDish(id);  // gọi 1 lần duy nhất
    System.out.println("Delete request for DishId: " + id);
    System.out.println("Deleted result: " + deleted);
    resp.sendRedirect(req.getContextPath() + "/admin/dishes");
}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        String name = req.getParameter("name");
        String image = req.getParameter("image");
        String desc = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        String catName = req.getParameter("categoryName");
         
        String[] ingIds = req.getParameterValues("ingredientIds");
        List<Ingredient> ingredientList = new ArrayList<>();
        if (ingIds != null) {
            for (String ingId : ingIds) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(Integer.parseInt(ingId));
                ingredientList.add(ing);
            }
        }


        if (action.equals("add")) {
            Dish d = new Dish();
            d.setDishName(name);
            d.setDishImageUrl(image);
            d.setDishDescription(desc);
            d.setDishPrice(price);
            d.setCategoryName(catName);
            d.setIngredients(ingredientList);
            dao.addDish(d);

        } else if (action.equals("update")) {
            int id = Integer.parseInt(req.getParameter("dishId"));
            Dish d = new Dish();
            d.setDishId(id);
            d.setDishName(name);
            d.setDishImageUrl(image);
            d.setDishDescription(desc);
            d.setDishPrice(price);
            d.setCategoryName(catName);
            d.setIngredients(ingredientList);
            dao.updateDish(d);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/dishes");
    }
}
