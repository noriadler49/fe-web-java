package com.mycompany.maven.mvc.project.model;

public class CartItem {
    private int cartItemId;
    private int dishId;
    private int quantity;
    private int accountId;
    private String dishName;
    private double price;
    private Dish dish;

    public CartItem() {}

    public CartItem(int cartItemId, int dishId, int quantity, int accountId, String dishName, double price, Dish dish) {
        this.cartItemId = cartItemId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.accountId = accountId;
        this.dishName = dishName;
        this.price = price;
        this.dish = dish;
    }

    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }
}
