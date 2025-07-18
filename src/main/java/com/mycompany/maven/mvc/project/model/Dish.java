package com.mycompany.maven.mvc.project.model;

import java.sql.Timestamp;
import java.util.List;

public class Dish {
    private int dishId;
    private String dishName;
    private String dishImageUrl;
    private String dishDescription;
    private double dishPrice;
    private String categoryName;
    private Timestamp dishCreatedAt;
    private Timestamp dishUpdatedAt;
    private List<Ingredient> ingredients;

    public Dish() {}

    public Dish(int dishId, String dishName, String dishImageUrl, String dishDescription,
                double dishPrice, String categoryName, Timestamp dishCreatedAt,
                Timestamp dishUpdatedAt, List<Ingredient> ingredients) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImageUrl = dishImageUrl;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.categoryName = categoryName;
        this.dishCreatedAt = dishCreatedAt;
        this.dishUpdatedAt = dishUpdatedAt;
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

    public Timestamp getDishCreatedAt() { return dishCreatedAt; }
    public void setDishCreatedAt(Timestamp dishCreatedAt) { this.dishCreatedAt = dishCreatedAt; }

    public Timestamp getDishUpdatedAt() { return dishUpdatedAt; }
    public void setDishUpdatedAt(Timestamp dishUpdatedAt) { this.dishUpdatedAt = dishUpdatedAt; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
}
