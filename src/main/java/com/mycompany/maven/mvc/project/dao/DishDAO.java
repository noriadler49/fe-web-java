package com.mycompany.maven.mvc.project.dao;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.model.Ingredient;
import com.mycompany.maven.mvc.project.resources.DBContext;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.regex.Pattern;

public class DishDAO {

    private final MongoDatabase database = DBContext.getDatabase();
    private final MongoCollection<Document> dishCol = database.getCollection("tbl_Dishes");
    private final MongoCollection<Document> categoryCol = database.getCollection("tbl_Categories");
    private final MongoCollection<Document> ingredientCol = database.getCollection("tbl_Ingredients");
    private final MongoCollection<Document> dishIngredientCol = database.getCollection("tbl_DishIngredients");

    public List<Dish> getAllDishes(String category, String search) {
        List<Bson> filters = new ArrayList<>();

        if (!"All".equalsIgnoreCase(category)) {
            filters.add(Filters.eq("CategoryName", category));
        }

        if (search != null && !search.trim().isEmpty()) {
            filters.add(Filters.regex("DishName", Pattern.compile(search, Pattern.CASE_INSENSITIVE)));
        }

        Bson query = filters.isEmpty() ? new Document() : Filters.and(filters);
        FindIterable<Document> docs = dishCol.find(query).sort(Sorts.descending("DishCreatedAt"));

        List<Dish> dishes = new ArrayList<>();
        for (Document doc : docs) {
            dishes.add(documentToDish(doc));
        }

        return dishes;
    }

    public List<Dish> getRandomDishes(int limit) {
        List<Dish> dishes = new ArrayList<>();
        AggregateIterable<Document> result = dishCol.aggregate(List.of(
                Aggregates.sample(limit)
        ));

        for (Document doc : result) {
            dishes.add(documentToDish(doc));
        }

        return dishes;
    }

    public Dish getDishById(int dishId) {
        Document doc = dishCol.find(Filters.eq("DishId", dishId)).first();
        if (doc == null) return null;

        Dish dish = documentToDish(doc);
        dish.setIngredients(getIngredientsByDishId(dishId));
        return dish;
    }

    private List<Ingredient> getIngredientsByDishId(int dishId) {
        List<Ingredient> ingredients = new ArrayList<>();

        // Tìm tất cả IngredientId từ bảng DishIngredients
        FindIterable<Document> dishIngDocs = dishIngredientCol.find(Filters.eq("DishId", dishId));
        List<Integer> ingredientIds = new ArrayList<>();
        for (Document doc : dishIngDocs) {
            ingredientIds.add(doc.getInteger("IngredientId"));
        }

        // Lấy thông tin từ Ingredients collection
        FindIterable<Document> ingDocs = ingredientCol.find(Filters.in("IngredientId", ingredientIds));
        for (Document doc : ingDocs) {
            Ingredient ing = new Ingredient();
            ing.setIngredientId(doc.getInteger("IngredientId"));
            ing.setIngredientName(doc.getString("IngredientName"));
            ingredients.add(ing);
        }

        return ingredients;
    }

    public List<String> getAllCategoryNames() {
        List<String> categories = new ArrayList<>();
        FindIterable<Document> docs = categoryCol.find().sort(Sorts.ascending("CategoryName"));

        for (Document doc : docs) {
            categories.add(doc.getString("CategoryName"));
        }

        return categories;
    }

    public boolean addDish(Dish dish) {
        // Auto-generate DishId (tăng tự động)
        int newId = getNextDishId();

        Document doc = new Document("DishId", newId)
                .append("DishName", dish.getDishName())
                .append("DishImageUrl", dish.getDishImageUrl())
                .append("DishDescription", dish.getDishDescription())
                .append("DishPrice", dish.getDishPrice())
                .append("CategoryName", dish.getCategoryName())
                .append("DishCreatedAt", new Date())
                .append("DishUpdatedAt", new Date());

        dishCol.insertOne(doc);
        return true;
    }

    public boolean updateDish(Dish dish) {
        Bson filter = Filters.eq("DishId", dish.getDishId());

        Bson updates = Updates.combine(
                Updates.set("DishName", dish.getDishName()),
                Updates.set("DishImageUrl", dish.getDishImageUrl()),
                Updates.set("DishDescription", dish.getDishDescription()),
                Updates.set("DishPrice", dish.getDishPrice()),
                Updates.set("CategoryName", dish.getCategoryName()),
                Updates.set("DishUpdatedAt", new Date())
        );

        UpdateResult result = dishCol.updateOne(filter, updates);
        return result.getModifiedCount() > 0;
    }

    public boolean deleteDish(int dishId) {
        DeleteResult result = dishCol.deleteOne(Filters.eq("DishId", dishId));
        return result.getDeletedCount() > 0;
    }

    private Dish documentToDish(Document doc) {
        Dish dish = new Dish();
        dish.setDishId(doc.getInteger("DishId"));
        dish.setDishName(doc.getString("DishName"));
        dish.setDishImageUrl(doc.getString("DishImageUrl"));
        dish.setDishDescription(doc.getString("DishDescription"));
        dish.setDishPrice(doc.getDouble("DishPrice"));
        dish.setCategoryName(doc.getString("CategoryName"));
        return dish;
    }

    private int getNextDishId() {
        // Lấy DishId lớn nhất, tăng lên 1
        Document doc = dishCol.find()
                .sort(Sorts.descending("DishId"))
                .limit(1)
                .first();

        return doc == null ? 1 : doc.getInteger("DishId") + 1;
    }
}
