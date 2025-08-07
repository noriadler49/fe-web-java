package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.Account;
import com.mycompany.maven.mvc.project.resources.DBContext;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final MongoDatabase database = DBContext.getDatabase();
    private final MongoCollection<Document> collection = database.getCollection("tbl_Accounts");

    // ✅ Kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        Document found = collection.find(and(
                eq("AccountUsername", username),
                eq("AccountPassword", password)
        )).first();
        return found != null;
    }

    // ✅ Đăng ký tài khoản mới
    public boolean register(Account acc) {
        try {
            Document doc = new Document("AccountUsername", acc.getAccountUsername())
                    .append("AccountPassword", acc.getAccountPassword())
                    .append("AccountRole", acc.getAccountRole());
            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error in register(): " + e.getMessage());
            return false;
        }
    }

    // ✅ Lấy tổng số tài khoản
    public int getTotalUsers() {
        return (int) collection.countDocuments();
    }

    // ✅ Lấy tài khoản theo username + password
    public Account getAccountByUsernameAndPassword(String username, String password) {
        Document doc = collection.find(and(
                eq("AccountUsername", username),
                eq("AccountPassword", password)
        )).first();

        if (doc != null) return documentToAccount(doc);
        return null;
    }

    // ✅ Lấy tài khoản theo ID (MongoDB _id hoặc trường riêng)
    public Account getAccountById(int accountId) {
        Document doc = collection.find(eq("AccountId", accountId)).first();
        if (doc != null) return documentToAccount(doc);
        return null;
    }

    // ✅ Lấy tất cả tài khoản
    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(documentToAccount(doc));
        }
        return list;
    }

    // ✅ Xoá tài khoản theo ID
    public boolean deleteAccountById(int accountId) {
        return collection.deleteOne(eq("AccountId", accountId)).getDeletedCount() > 0;
    }

    // ✅ Cập nhật thông tin tài khoản
    public boolean updateAccount(Account acc) {
        Document update = new Document("$set", new Document("AccountUsername", acc.getAccountUsername())
                .append("AccountPassword", acc.getAccountPassword()));
        return collection.updateOne(eq("AccountId", acc.getAccountId()), update).getModifiedCount() > 0;
    }

    // ✅ Toggle giữa "User" và "Staff"
    public boolean toggleAccountRole(int accountId) {
        Document doc = collection.find(eq("AccountId", accountId)).first();
        if (doc != null) {
            String currentRole = doc.getString("AccountRole");
            String newRole = currentRole.equals("User") ? "Staff" : "User";

            Document update = new Document("$set", new Document("AccountRole", newRole));
            return collection.updateOne(eq("AccountId", accountId), update).getModifiedCount() > 0;
        }
        return false;
    }

    // ✅ Hàm chuyển Document → Account
    private Account documentToAccount(Document doc) {
        Account acc = new Account();
        acc.setAccountId(doc.getInteger("AccountId", 0)); // Nếu không có thì default = 0
        acc.setAccountUsername(doc.getString("AccountUsername"));
        acc.setAccountPassword(doc.getString("AccountPassword"));
        acc.setAccountRole(doc.getString("AccountRole"));
        return acc;
    }
}
