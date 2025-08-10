package com.mycompany.maven.mvc.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.maven.mvc.project.model.CartItem;
import com.mycompany.maven.mvc.project.model.Order;
import com.mycompany.maven.mvc.project.model.OrderItem;
import com.mycompany.maven.mvc.project.resources.DBContext;

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
                order.setAccountId(rs.getInt("AccountId"));
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
            conn.setAutoCommit(false); // Start transaction

            // 1. Get AccountId
            String getUserSql = "SELECT AccountId FROM tbl_Accounts WHERE AccountUsername = ?";
            try (PreparedStatement getUserStmt = conn.prepareStatement(getUserSql)) {
                getUserStmt.setString(1, username);
                ResultSet userRs = getUserStmt.executeQuery();
                if (!userRs.next()) return -1;
                int accountId = userRs.getInt("AccountId");

                // 2. Insert into tbl_Orders
                String insertOrderSql = """
                    INSERT INTO tbl_Orders (OrderTotalPrice, AccountId, OrderStatus, VoucherCode, OrderAddress, PaymentMethod)
                    OUTPUT INSERTED.OrderId
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

                stmtOrder = conn.prepareStatement(insertOrderSql);
                stmtOrder.setDouble(1, total);
                stmtOrder.setInt(2, accountId);
                stmtOrder.setString(3, "Pending");

                if (voucherCode == null || voucherCode.trim().isEmpty()) {
                    stmtOrder.setNull(4, Types.VARCHAR);
                } else {
                    stmtOrder.setString(4, voucherCode);
                }

                stmtOrder.setString(5, address);
                stmtOrder.setString(6, payment);

                ResultSet rsOrder = stmtOrder.executeQuery();
                if (rsOrder.next()) {
                    orderId = rsOrder.getInt("OrderId");
                }

                // 3. Insert order items
                String insertItemSql = """
                    INSERT INTO tbl_OrderItems (DishId, OrderItemQuantity, OrderItemPrice, OrderId)
                    VALUES (?, ?, ?, ?)
                """;
                stmtItems = conn.prepareStatement(insertItemSql);

                for (CartItem item : cartItems) {
                    stmtItems.setInt(1, item.getDishId());
                    stmtItems.setInt(2, item.getQuantity());
                    stmtItems.setDouble(3, item.getPrice());
                    stmtItems.setInt(4, orderId);
                    stmtItems.addBatch();
                }

                stmtItems.executeBatch();
                conn.commit(); // Commit transaction
            }

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

    public Order getOrderByIdAndUsername(int orderId, String username) {
        Order order = null;
        try (Connection conn = DBContext.getConnection()) {
            String sql = """
                SELECT o.* FROM tbl_Orders o
                JOIN tbl_Accounts a ON o.AccountId = a.AccountId
                WHERE o.OrderId = ? AND a.AccountUsername = ?
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("OrderId"));
                order.setTotalPrice(rs.getDouble("OrderTotalPrice"));
                order.setStatus(rs.getString("OrderStatus"));
                order.setVoucherCode(rs.getString("VoucherCode"));
                order.setAccountId(rs.getInt("AccountId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
    List<OrderItem> items = new ArrayList<>();

    String sql = """
        SELECT oi.OrderItemId, oi.DishId, oi.OrderId,
               oi.OrderItemQuantity, oi.OrderItemPrice,
               d.DishName
        FROM tbl_OrderItems oi
        JOIN tbl_Dishes d ON oi.DishId = d.DishId
        WHERE oi.OrderId = ?
    """;

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, orderId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("OrderItemId"));
                item.setDishId(rs.getInt("DishId"));
                item.setOrderId(rs.getInt("OrderId"));
                item.setQuantity(rs.getInt("OrderItemQuantity"));
                item.setPrice(rs.getDouble("OrderItemPrice"));
                item.setDishName(rs.getString("DishName"));

                items.add(item);
            }
        }

    } catch (Exception e) {
        e.printStackTrace(); // Có thể thay bằng log nếu muốn
    }

    return items;
}


    public List<Order> getAllOrders(String status) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBContext.getConnection()) {
            String sql = """
                SELECT o.*, a.AccountUsername FROM tbl_Orders o
                JOIN tbl_Accounts a ON o.AccountId = a.AccountId
            """;
            if (!"All".equalsIgnoreCase(status)) {
                sql += " WHERE o.OrderStatus = ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            if (!"All".equalsIgnoreCase(status)) ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderId"));
                order.setTotalPrice(rs.getDouble("OrderTotalPrice"));
                order.setStatus(rs.getString("OrderStatus"));
                order.setVoucherCode(rs.getString("VoucherCode"));
                order.setAccountId(rs.getInt("AccountId"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    public boolean updateOrderStatus(int orderId, String status) {
    String sql = "UPDATE tbl_Orders SET OrderStatus = ? WHERE OrderId = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, orderId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
