package com.mycompany.maven.mvc.project.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private double totalPrice;
    private int accountId;
    private String status;
    private String voucherCode;
    private LocalDateTime createdAt;
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(int orderId, double totalPrice, int accountId, String status,
                 String voucherCode, LocalDateTime createdAt, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
        this.status = status;
        this.voucherCode = voucherCode;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
