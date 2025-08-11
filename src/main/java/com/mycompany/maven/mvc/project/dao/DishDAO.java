package com.mycompany.maven.mvc.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.model.Ingredient;
import com.mycompany.maven.mvc.project.resources.DBContext;

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

        // sql += " ORDER BY d.DishCreatedAt DESC";
        sql += " ORDER BY d.DishId ASC";
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
                 dish.setIngredients(getIngredientsByDishId(dish.getDishId()));
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
    public List<Ingredient> getAllIngredients() {
    List<Ingredient> list = new ArrayList<>();
    String sql = "SELECT IngredientId, IngredientName FROM tbl_Ingredients ORDER BY IngredientName";
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
            ing.setIngredientName(rs.getString("IngredientName"));
            list.add(ing);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
    }

    // Lấy tên tất cả categories
    public List<String> getAllCategoryNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT CategoryName FROM tbl_Categories ORDER BY CategoryName";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("CategoryName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Insert dish với categoryName
    // public boolean addDish(Dish dish) {
    //     String sql = """
    //         INSERT INTO tbl_Dishes (DishName, DishImageUrl, DishDescription, DishPrice, CategoryId, DishCreatedAt, DishUpdatedAt)
    //         VALUES (?, ?, ?, ?, (SELECT CategoryId FROM tbl_Categories WHERE CategoryName = ?), GETDATE(), GETDATE())
    //         """;
    //     try (Connection conn = DBContext.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql)) {

    //         ps.setString(1, dish.getDishName());
    //         ps.setString(2, dish.getDishImageUrl());
    //         ps.setString(3, dish.getDishDescription());
    //         ps.setDouble(4, dish.getDishPrice());
    //         ps.setString(5, dish.getCategoryName());

            
    //         return ps.executeUpdate() > 0;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }
    public boolean addDish(Dish dish) {
    String sqlDish = """
        INSERT INTO tbl_Dishes (DishName, DishImageUrl, DishDescription, DishPrice, CategoryId, DishCreatedAt, DishUpdatedAt)
        VALUES (?, ?, ?, ?, (SELECT CategoryId FROM tbl_Categories WHERE CategoryName = ?), GETDATE(), GETDATE())
    """;

    try (Connection conn = DBContext.getConnection();
         PreparedStatement psDish = conn.prepareStatement(sqlDish, PreparedStatement.RETURN_GENERATED_KEYS)
) {

        conn.setAutoCommit(false); // Start transaction

        // Insert dish
        psDish.setString(1, dish.getDishName());
        psDish.setString(2, dish.getDishImageUrl());
        psDish.setString(3, dish.getDishDescription());
        psDish.setDouble(4, dish.getDishPrice());
        psDish.setString(5, dish.getCategoryName());
        psDish.executeUpdate();

        // Get generated DishId
        int newDishId = 0;
        try (ResultSet rs = psDish.getGeneratedKeys()) {
            if (rs.next()) {
                newDishId = rs.getInt(1);
            }
        }

        // Insert ingredients
        if (dish.getIngredients() != null) {
            String sqlIng = "INSERT INTO tbl_DishIngredients (DishId, IngredientId) VALUES (?, ?)";
            try (PreparedStatement psIng = conn.prepareStatement(sqlIng)) {
                for (var ing : dish.getIngredients()) {
                    psIng.setInt(1, newDishId);
                    psIng.setInt(2, ing.getIngredientId());
                    psIng.addBatch();
                }
                psIng.executeBatch();
            }
        }

        conn.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    // Update dish cùng categoryName
    // public boolean updateDish(Dish dish) {
    //     String sql = """
    //         UPDATE tbl_Dishes
    //         SET DishName = ?, DishImageUrl = ?, DishDescription = ?, DishPrice = ?,
    //             CategoryId = (SELECT CategoryId FROM tbl_Categories WHERE CategoryName = ?),
    //             DishUpdatedAt = GETDATE()
    //         WHERE DishId = ?
    //         """;
    //     try (Connection conn = DBContext.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql)) {

    //         ps.setString(1, dish.getDishName());
    //         ps.setString(2, dish.getDishImageUrl());
    //         ps.setString(3, dish.getDishDescription());
    //         ps.setDouble(4, dish.getDishPrice());
    //         ps.setString(5, dish.getCategoryName());
    //         ps.setInt(6, dish.getDishId());
    //         return ps.executeUpdate() > 0;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }
    public boolean updateDish(Dish dish) {
    String sqlDish = """
        UPDATE tbl_Dishes
        SET DishName = ?, DishImageUrl = ?, DishDescription = ?, DishPrice = ?,
            CategoryId = (SELECT CategoryId FROM tbl_Categories WHERE CategoryName = ?),
            DishUpdatedAt = GETDATE()
        WHERE DishId = ?
    """;

    try (Connection conn = DBContext.getConnection();
         PreparedStatement psDish = conn.prepareStatement(sqlDish)) {

        conn.setAutoCommit(false);

        // Update dish
        psDish.setString(1, dish.getDishName());
        psDish.setString(2, dish.getDishImageUrl());
        psDish.setString(3, dish.getDishDescription());
        psDish.setDouble(4, dish.getDishPrice());
        psDish.setString(5, dish.getCategoryName());
        psDish.setInt(6, dish.getDishId());
        psDish.executeUpdate();

        // Delete old ingredients
        try (PreparedStatement psDel = conn.prepareStatement("DELETE FROM tbl_DishIngredients WHERE DishId = ?")) {
            psDel.setInt(1, dish.getDishId());
            psDel.executeUpdate();
        }

        // Insert new ingredients
        if (dish.getIngredients() != null) {
            String sqlIng = "INSERT INTO tbl_DishIngredients (DishId, IngredientId) VALUES (?, ?)";
            try (PreparedStatement psIng = conn.prepareStatement(sqlIng)) {
                for (var ing : dish.getIngredients()) {
                    psIng.setInt(1, dish.getDishId());
                    psIng.setInt(2, ing.getIngredientId());
                    psIng.addBatch();
                }
                psIng.executeBatch();
            }
        }

        conn.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    // Delete dish
    // public boolean deleteDish(int dishId) {
    //     String sql = "DELETE FROM tbl_Dishes WHERE DishId = ?";
    //     try (Connection conn = DBContext.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql)) {

    //         ps.setInt(1, dishId);
    //         return ps.executeUpdate() > 0;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }
    public boolean deleteDish(int dishId) {
    String sqlDeleteIngredients = "DELETE FROM tbl_DishIngredients WHERE DishId = ?";
    String sqlDeleteDish = "DELETE FROM tbl_Dishes WHERE DishId = ?";
    Connection conn = null;
    try {
        conn = DBContext.getConnection();
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(sqlDeleteIngredients)) {
            ps1.setInt(1, dishId);
            ps1.executeUpdate();
        }

        try (PreparedStatement ps2 = conn.prepareStatement(sqlDeleteDish)) {
            ps2.setInt(1, dishId);
            ps2.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    return false;
}

}

