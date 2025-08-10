package com.mycompany.maven.mvc.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.maven.mvc.project.model.Ingredient;
import com.mycompany.maven.mvc.project.resources.DBContext;

public class IngredientDAO {

    // Lấy tất cả nguyên liệu
    // IngredientDAO - getAllIngredients (sắp theo IngredientId)
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT IngredientId, IngredientName FROM tbl_Ingredients ORDER BY IngredientId";
        try (Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(rs.getInt("IngredientId"));
                ing.setIngredientName(rs.getString("IngredientName"));
                list.add(ing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // Lấy nguyên liệu theo ID
    public Ingredient getIngredientById(int ingredientId) {
        String sql = "SELECT * FROM tbl_Ingredients WHERE IngredientId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ingredientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(rs.getInt("IngredientId"));
                ing.setIngredientName(rs.getString("IngredientName"));
                return ing;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm nguyên liệu mới
    public boolean addIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO tbl_Ingredients (IngredientName) VALUES (?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ingredient.getIngredientName());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật nguyên liệu
    public boolean updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE tbl_Ingredients SET IngredientName = ? WHERE IngredientId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ingredient.getIngredientName());
            ps.setInt(2, ingredient.getIngredientId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa nguyên liệu
    public boolean deleteIngredient(int ingredientId) {
        String sql = "DELETE FROM tbl_Ingredients WHERE IngredientId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ingredientId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy nguyên liệu theo DishId
    public List<Ingredient> getIngredientsByDishId(int dishId) {
        List<Ingredient> list = new ArrayList<>();
        String sql = """
            SELECT i.IngredientId, i.IngredientName
            FROM tbl_Ingredients i
            JOIN tbl_DishIngredients di ON i.IngredientId = di.IngredientId
            WHERE di.DishId = ?
        """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIngredientId(rs.getInt("IngredientId"));
                ing.setIngredientName(rs.getString("IngredientName"));
                list.add(ing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Gán nguyên liệu cho món ăn
    public boolean addIngredientToDish(int dishId, int ingredientId) {
        String sql = "INSERT INTO tbl_DishIngredients (DishId, IngredientId) VALUES (?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa nguyên liệu khỏi món ăn
    public boolean removeIngredientFromDish(int dishId, int ingredientId) {
        String sql = "DELETE FROM tbl_DishIngredients WHERE DishId = ? AND IngredientId = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
