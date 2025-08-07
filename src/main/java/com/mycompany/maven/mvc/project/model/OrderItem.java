package com.mycompany.maven.mvc.project.model;

public class OrderItem {
    private String orderItemId;
    private String dishId;
    private String dishName;
    private int quantity;
    private double price;
    private String orderId;

    public OrderItem() {}

    public OrderItem(String orderItemId, String dishId, int quantity, double price, String orderId) {
        this.orderItemId = orderItemId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    // Getter & Setter
    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }

    public String getDishId() { return dishId; }
    public void setDishId(String dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; } 
    public void setDishName(String dishName) { this.dishName = dishName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public void setOrderItemPrice(double price) { this.price = price; }

    public void setOrderItemQuantity(int quantity) { this.quantity = quantity; }
}
