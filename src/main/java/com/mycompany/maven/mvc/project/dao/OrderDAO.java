package com.mycompany.maven.mvc.project.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.maven.mvc.project.model.CartItem;
import com.mycompany.maven.mvc.project.model.Order;
import com.mycompany.maven.mvc.project.model.OrderItem;
import com.mycompany.maven.mvc.project.resources.DBContext;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class OrderDAO {

    private final MongoDatabase database;

    public OrderDAO() {
        this.database = DBContext.getDatabase();
    }

    public List<Order> getOrdersByUsernameAndStatus(String username, String status) {
        List<Order> orders = new ArrayList<>();

        MongoCollection<Document> ordersCol = database.getCollection("tbl_Orders");
        MongoCollection<Document> accountsCol = database.getCollection("tbl_Accounts");

        Document account = accountsCol.find(eq("AccountUsername", username)).first();
        if (account == null) return orders;

        ObjectId accountId = account.getObjectId("_id");

        var filter = eq("AccountId", accountId);
        if (!"All".equalsIgnoreCase(status)) {
            filter = and(filter, eq("OrderStatus", status));
        }

        for (Document doc : ordersCol.find(filter)) {
            Order order = new Order();
            order.setOrderId(doc.getObjectId("_id").toHexString());
            order.setTotalPrice(doc.getDouble("OrderTotalPrice"));
            order.setStatus(doc.getString("OrderStatus"));
            order.setVoucherCode(doc.getString("VoucherCode"));
            order.setAccountId(accountId.toHexString());
            orders.add(order);
        }

        return orders;
    }

    public String placeOrder(String username, List<CartItem> cartItems, double total,
                             String voucherCode, String address, String payment) {
        MongoCollection<Document> accountsCol = database.getCollection("tbl_Accounts");
        MongoCollection<Document> ordersCol = database.getCollection("tbl_Orders");
        MongoCollection<Document> orderItemsCol = database.getCollection("tbl_OrderItems");

        Document account = accountsCol.find(eq("AccountUsername", username)).first();
        if (account == null) return null;

        ObjectId accountId = account.getObjectId("_id");

        Document orderDoc = new Document("OrderTotalPrice", total)
                .append("AccountId", accountId)
                .append("OrderStatus", "Pending")
                .append("VoucherCode", voucherCode)
                .append("OrderAddress", address)
                .append("PaymentMethod", payment);

        ordersCol.insertOne(orderDoc);
        ObjectId orderId = orderDoc.getObjectId("_id");

        List<Document> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            Document doc = new Document("OrderId", orderId)
                    .append("DishId", new ObjectId())
                    .append("Quantity", item.getQuantity())
                    .append("Price", item.getDish().getDishPrice());
            orderItems.add(doc);
        }

        if (!orderItems.isEmpty()) {
            orderItemsCol.insertMany(orderItems);
        }

        return orderId.toHexString();
    }

    public Order getOrderByIdAndUsername(String orderId, String username) {
        MongoCollection<Document> ordersCol = database.getCollection("tbl_Orders");
        MongoCollection<Document> accountsCol = database.getCollection("tbl_Accounts");

        Document account = accountsCol.find(eq("AccountUsername", username)).first();
        if (account == null) return null;

        ObjectId accId = account.getObjectId("_id");
        Document orderDoc = ordersCol.find(and(
                eq("_id", new ObjectId()),
                eq("AccountId", accId)
        )).first();

        if (orderDoc == null) return null;

        Order order = new Order();
        order.setOrderId(orderDoc.getObjectId("_id").toHexString());
        order.setTotalPrice(orderDoc.getDouble("OrderTotalPrice"));
        order.setStatus(orderDoc.getString("OrderStatus"));
        order.setVoucherCode(orderDoc.getString("VoucherCode"));
        order.setAccountId(accId.toHexString());

        return order;
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        List<OrderItem> items = new ArrayList<>();

        MongoCollection<Document> orderItemsCol = database.getCollection("tbl_OrderItems");
        MongoCollection<Document> dishesCol = database.getCollection("tbl_Dishes");

        ObjectId oid = new ObjectId(orderId);

        for (Document doc : orderItemsCol.find(eq("OrderId", oid))) {
            OrderItem item = new OrderItem();
            item.setOrderItemId(doc.getObjectId("_id").toHexString());
            item.setOrderItemId(orderId);
            item.setDishId(doc.getObjectId("DishId").toHexString());
            item.setQuantity(doc.getInteger("Quantity"));
            item.setPrice(doc.getDouble("Price"));

            Document dishDoc = dishesCol.find(eq("_id", doc.getObjectId("DishId"))).first();
            if (dishDoc != null) {
                item.setDishName(dishDoc.getString("DishName"));
            }

            items.add(item);
        }

        return items;
    }

    public List<Order> getAllOrders(String status) {
        List<Order> orders = new ArrayList<>();

        MongoCollection<Document> ordersCol = database.getCollection("tbl_Orders");

        var filter = new Document();
        if (!"All".equalsIgnoreCase(status)) {
            filter.append("OrderStatus", status);
        }

        for (Document doc : ordersCol.find(filter)) {
            Order order = new Order();
            order.setOrderId(doc.getObjectId("_id").toHexString());
            order.setTotalPrice(doc.getDouble("OrderTotalPrice"));
            order.setStatus(doc.getString("OrderStatus"));
            order.setVoucherCode(doc.getString("VoucherCode"));
            order.setAccountId(doc.getObjectId("AccountId").toHexString());
            orders.add(order);
        }

        return orders;
    }
}
