package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.CartItem;
import com.mycompany.maven.mvc.project.model.Order;
import com.mycompany.maven.mvc.project.model.OrderItem;
import com.mycompany.maven.mvc.project.resources.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getOrdersByUsernameAndStatus(String username, String status) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            String sql = "SELECT * FROM tbl_Orders o JOIN tbl_Accounts a ON o.AccountId = a.AccountId WHERE a.AccountUsername = ?";
            if (!"All".equalsIgnoreCase(status)) {
                sql += " AND o.OrderStatus = ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            if (!"All".equalsIgnoreCase(status)) ps.setString(2, status);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderId"));
                order.setTotalPrice(rs.getDouble("OrderTotalPrice"));
                order.setStatus(rs.getString("OrderStatus"));
                order.setVoucherCode(rs.getString("VoucherCode"));
                Timestamp created = rs.getTimestamp("OrderCreatedAt");
                if (created != null) {
                    order.setCreatedAt(created.toLocalDateTime());
                }
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int placeOrder(String username, List<CartItem> cartItems, double total, String voucherCode, String address, String payment) {
        int orderId = -1;
        Connection conn = null;
        PreparedStatement stmtOrder = null;
        PreparedStatement stmtItems = null;

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Transaction start

            // 1. Get AccountId from username
            String getUserSql = "SELECT AccountId FROM tbl_Accounts WHERE AccountUsername = ?";
            PreparedStatement getUserStmt = conn.prepareStatement(getUserSql);
            getUserStmt.setString(1, username);
            ResultSet userRs = getUserStmt.executeQuery();
            if (!userRs.next()) return -1; // user not found
            int accountId = userRs.getInt("AccountId");

            // 2. Insert into tbl_Orders and get OrderId
            String insertOrderSql = "INSERT INTO tbl_Orders (OrderTotalPrice, AccountId, OrderStatus, VoucherCode, OrderCreatedAt) " +
                    "OUTPUT INSERTED.OrderId VALUES (?, ?, ?, ?, GETDATE())";
            stmtOrder = conn.prepareStatement(insertOrderSql);
            stmtOrder.setDouble(1, total);
            stmtOrder.setInt(2, accountId);
            stmtOrder.setString(3, "Pending"); // default status
            stmtOrder.setString(4, voucherCode);

            ResultSet rsOrder = stmtOrder.executeQuery();
            if (rsOrder.next()) {
                orderId = rsOrder.getInt("OrderId");
            }

            // 3. Insert each CartItem into tbl_OrderItems
            String insertItemSql = "INSERT INTO tbl_OrderItems (DishId, OrderItemQuantity, OrderItemPrice, OrderId) " +
                    "VALUES (?, ?, ?, ?)";
            stmtItems = conn.prepareStatement(insertItemSql);

            for (CartItem item : cartItems) {
                stmtItems.setInt(1, item.getDishId());
                stmtItems.setInt(2, item.getQuantity());
                stmtItems.setDouble(3, item.getPrice());
                stmtItems.setInt(4, orderId);
                stmtItems.addBatch();
            }
            stmtItems.executeBatch();

            conn.commit(); // Transaction commit

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (stmtOrder != null) stmtOrder.close(); } catch (Exception e) {}
            try { if (stmtItems != null) stmtItems.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
        }

        return orderId;
    }
}
