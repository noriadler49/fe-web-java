package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.CartItem;
import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.resources.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public List<CartItem> getCartItemsByUsername(String username) {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            String sql = """
            SELECT c.CartItemId, c.CartItemQuantity, d.DishId, d.DishName, d.DishPrice
            FROM tbl_CartItems c
            JOIN tbl_Dishes d ON c.DishId = d.DishId
            JOIN tbl_Accounts a ON a.AccountId = c.AccountId
            WHERE a.AccountUsername = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("CartItemId"));
                item.setQuantity(rs.getInt("CartItemQuantity"));
                item.setPrice(rs.getDouble("DishPrice")); // use DishPrice as item price

                // Set Dish object
                Dish dish = new Dish();
                dish.setDishId(rs.getInt("DishId"));
                dish.setDishName(rs.getString("DishName"));
                dish.setDishPrice(rs.getDouble("DishPrice"));
                item.setDish(dish);

                cartItems.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void clearCart(String username) {
        try (Connection conn = DBContext.getConnection()) {
            String sql = """
                    DELETE c
                    FROM tbl_CartItems c
                    JOIN tbl_Accounts a ON c.AccountId = a.AccountId
                    WHERE a.AccountUsername = ?
                    """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(String username, int dishId, int quantity) {
        String sqlCheck = "SELECT * FROM tbl_CartItems WHERE AccountId = (SELECT AccountId FROM tbl_Accounts WHERE AccountUsername = ?) AND DishId = ?";
        String sqlUpdate = "UPDATE tbl_CartItems SET CartItemQuantity = CartItemQuantity + ? WHERE AccountId = (SELECT AccountId FROM tbl_Accounts WHERE AccountUsername = ?) AND DishId = ?";
        String sqlInsert = "INSERT INTO tbl_CartItems (AccountId, DishId, CartItemQuantity) VALUES ((SELECT AccountId FROM tbl_Accounts WHERE AccountUsername = ?), ?, ?)";

        try (Connection conn = DBContext.getConnection();
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {

            psCheck.setString(1, username);
            psCheck.setInt(2, dishId);

            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, quantity);
                    psUpdate.setString(2, username);
                    psUpdate.setInt(3, dishId);
                    psUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                    psInsert.setString(1, username);
                    psInsert.setInt(2, dishId);
                    psInsert.setInt(3, quantity);
                    psInsert.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
