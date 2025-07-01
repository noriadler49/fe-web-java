package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.model.Ingredient;
import com.mycompany.maven.mvc.project.resources.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {

    public List<Dish> getAllDishes(String category, String search) {
        
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT d.*, c.CategoryName FROM tbl_Dishes d JOIN tbl_Categories c ON d.CategoryId = c.CategoryId WHERE 1=1";

        if (!"All".equalsIgnoreCase(category)) {
            sql += " AND c.CategoryName = ?";
        }

        if (search != null && !search.trim().isEmpty()) {
            sql += " AND d.DishName LIKE ?";
        }

        sql += " ORDER BY d.DishCreatedAt DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (!"All".equalsIgnoreCase(category)) {
                ps.setString(paramIndex++, category);
            }

            if (search != null && !search.trim().isEmpty()) {
                ps.setString(paramIndex, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setDishId(rs.getInt("DishId"));
                dish.setDishName(rs.getString("DishName"));
                dish.setDishImageUrl(rs.getString("DishImageUrl"));
                dish.setDishDescription(rs.getString("DishDescription"));
                dish.setDishPrice(rs.getDouble("DishPrice"));
                dish.setCategoryName(rs.getString("CategoryName"));
                dish.setDishCreatedAt(rs.getTimestamp("DishCreatedAt"));
                dish.setDishUpdatedAt(rs.getTimestamp("DishUpdatedAt"));
                dishes.add(dish);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dishes;
    }

    public List<Dish> getRandomDishes(int limit) {
    List<Dish> dishes = new ArrayList<>();
    String sql = "SELECT TOP (?) d.*, c.CategoryName " +
                 "FROM tbl_Dishes d JOIN tbl_Categories c ON d.CategoryId = c.CategoryId " +
                 "ORDER BY NEWID()";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Dish dish = new Dish();
            dish.setDishId(rs.getInt("DishId"));
            dish.setDishName(rs.getString("DishName"));
            dish.setDishImageUrl(rs.getString("DishImageUrl"));
            dish.setDishDescription(rs.getString("DishDescription"));
            dish.setDishPrice(rs.getDouble("DishPrice"));
            dish.setCategoryName(rs.getString("CategoryName"));
            dish.setDishCreatedAt(rs.getTimestamp("DishCreatedAt"));
            dish.setDishUpdatedAt(rs.getTimestamp("DishUpdatedAt"));
            dishes.add(dish);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public Dish getDishById(int dishId) {
    String sql = "SELECT d.*, c.CategoryName FROM tbl_Dishes d JOIN tbl_Categories c ON d.CategoryId = c.CategoryId WHERE d.DishId = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, dishId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Dish dish = new Dish();
            dish.setDishId(rs.getInt("DishId"));
            dish.setDishName(rs.getString("DishName"));
            dish.setDishImageUrl(rs.getString("DishImageUrl"));
            dish.setDishDescription(rs.getString("DishDescription"));
            dish.setDishPrice(rs.getDouble("DishPrice"));
            dish.setCategoryName(rs.getString("CategoryName"));
            dish.setDishCreatedAt(rs.getTimestamp("DishCreatedAt"));
            dish.setDishUpdatedAt(rs.getTimestamp("DishUpdatedAt"));
            dish.setIngredients(getIngredientsByDishId(dishId));
            return dish;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    }
    private List<Ingredient> getIngredientsByDishId(int dishId) {
    List<Ingredient> list = new ArrayList<>();
    String sql = "SELECT i.IngredientId, i.IngredientName " +
                 "FROM tbl_Ingredients i " +
                 "JOIN tbl_DishIngredients di ON i.IngredientId = di.IngredientId " +
                 "WHERE di.DishId = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, dishId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Ingredient ing = new Ingredient();
            ing.setIngredientId(rs.getInt("IngredientId"));
            ing.setIngredientName(rs.getString("IngredientName")); // ✅ CHỖ NÀY
            list.add(ing);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}



}
