package com.mycompany.maven.mvc.project.model;

public class OrderItem {
    private int orderItemId;
    private int dishId;
    private String dishName;
    private int quantity;
    private double price;
    private int orderId;

    public OrderItem() {}

    public OrderItem(int orderItemId, int dishId, int quantity, double price, int orderId) {
        this.orderItemId = orderItemId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    // Getter & Setter
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; } 
    public void setDishName(String dishName) { this.dishName = dishName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    
    public void setOrderItemPrice(double price) { this.price = price; }

    public void setOrderItemQuantity(int quantity) { this.quantity = quantity; }
}
