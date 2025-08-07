package com.mycompany.maven.mvc.project.model;

import java.util.List;

public class Dish {
    private int dishId;
    private String dishName;
    private String dishImageUrl;
    private String dishDescription;
    private double dishPrice;
    private String categoryName;
    private List<Ingredient> ingredients;

    public Dish() {}

    public Dish(int dishId, String dishName, String dishImageUrl, String dishDescription,
                double dishPrice, String categoryName, List<Ingredient> ingredients) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImageUrl = dishImageUrl;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.categoryName = categoryName;
        this.ingredients = ingredients;
    }

    public int getDishId() { return dishId; }
    public void setDishId(int dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getDishImageUrl() { return dishImageUrl; }
    public void setDishImageUrl(String dishImageUrl) { this.dishImageUrl = dishImageUrl; }

    public String getDishDescription() { return dishDescription; }
    public void setDishDescription(String dishDescription) { this.dishDescription = dishDescription; }

    public double getDishPrice() { return dishPrice; }
    public void setDishPrice(double dishPrice) { this.dishPrice = dishPrice; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
}
