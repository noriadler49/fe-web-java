package com.mycompany.maven.mvc.project.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.maven.mvc.project.model.CartItem;
import com.mycompany.maven.mvc.project.model.Dish;
import com.mycompany.maven.mvc.project.resources.DBContext;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class CartDAO {

    private final MongoDatabase db = DBContext.getDatabase();
    private final MongoCollection<Document> accounts = db.getCollection("tbl_Accounts");
    private final MongoCollection<Document> dishes = db.getCollection("tbl_Dishes");
    private final MongoCollection<Document> cartItems = db.getCollection("tbl_CartItems");

    private Integer getAccountIdByUsername(String username) {
        Document acc = accounts.find(eq("accountUsername", username)).first();
        return acc != null ? acc.getInteger("accountId") : null;
    }

    public List<CartItem> getCartItemsByUsername(String username) {
        List<CartItem> cartList = new ArrayList<>();
        Integer accountId = getAccountIdByUsername(username);
        if (accountId == null) return cartList;

        for (Document doc : cartItems.find(eq("accountId", accountId))) {
            CartItem item = new CartItem();
            item.setCartItemId(doc.getInteger("cartItemId"));
            item.setDishId(doc.getInteger("dishId"));
            item.setQuantity(doc.getInteger("quantity"));
            item.setAccountId(accountId);

            // Lấy Dish từ collection dishes
            Document dishDoc = dishes.find(eq("dishId", doc.getInteger("dishId"))).first();
            if (dishDoc != null) {
                Dish dish = new Dish();
                dish.setDishId(dishDoc.getInteger("dishId"));
                dish.setDishName(dishDoc.getString("dishName"));
                dish.setDishPrice(dishDoc.getDouble("dishPrice"));
                item.setDish(dish);
            }

            cartList.add(item);
        }

        return cartList;
    }

    public void clearCart(String username) {
        Integer accountId = getAccountIdByUsername(username);
        if (accountId != null) {
            cartItems.deleteMany(eq("accountId", accountId));
        }
    }

    public void addToCart(String username, int dishId, int quantity) {
        Integer accountId = getAccountIdByUsername(username);
        if (accountId == null) return;

        Document existing = cartItems.find(and(
            eq("accountId", accountId),
            eq("dishId", dishId)
        )).first();

        if (existing != null) {
            Bson update = inc("quantity", quantity);
            cartItems.updateOne(eq("_id", existing.getObjectId("_id")), update);
        } else {
            int newCartItemId = (int) (System.currentTimeMillis() % 1000000000); // Tạm sinh ID
            Document doc = new Document("cartItemId", newCartItemId)
                    .append("accountId", accountId)
                    .append("dishId", dishId)
                    .append("quantity", quantity);
            cartItems.insertOne(doc);
        }
    }

    public void updateCartQuantity(String username, int cartItemId, int quantity) {
        Integer accountId = getAccountIdByUsername(username);
        if (accountId == null) return;

        Bson filter = and(eq("cartItemId", cartItemId), eq("accountId", accountId));

        if (quantity <= 0) {
            cartItems.deleteOne(filter);
        } else {
            cartItems.updateOne(filter, set("quantity", quantity));
        }
    }

    public void removeCartItem(String username, int cartItemId) {
        Integer accountId = getAccountIdByUsername(username);
        if (accountId == null) return;

        cartItems.deleteOne(and(eq("cartItemId", cartItemId), eq("accountId", accountId)));
    }
}
