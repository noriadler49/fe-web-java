package com.mycompany.maven.mvc.project.model;

import java.util.List;

public class Order {
    private String orderId;
    private double totalPrice;
    private String accountId;
    private String status;
    private String voucherCode;
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(String orderId, double totalPrice, String accountId, String status,
                 String voucherCode, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
        this.status = status;
        this.voucherCode = voucherCode;
        this.orderItems = orderItems;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}
