package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.Account;
import com.mycompany.maven.mvc.project.resources.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // Kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM tbl_Accounts WHERE AccountUsername=? AND AccountPassword=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký tài khoản mới
    public boolean register(Account acc) {
        String sql = "INSERT INTO tbl_Accounts (AccountUsername, AccountPassword, AccountRole) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, acc.getAccountUsername());
            ps.setString(2, acc.getAccountPassword());
            ps.setString(3, acc.getAccountRole());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in register(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lấy số lượng tài khoản
    public int getTotalUsers() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tbl_Accounts";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Lấy tài khoản theo tên người dùng và mật khẩu
    public Account getAccountByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM tbl_Accounts WHERE AccountUsername=? AND AccountPassword=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setAccountUsername(rs.getString("AccountUsername"));
                acc.setAccountPassword(rs.getString("AccountPassword"));
                acc.setAccountRole(rs.getString("AccountRole"));
                return acc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM tbl_Accounts WHERE AccountId=?";
        try (Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setAccountUsername(rs.getString("AccountUsername"));
                acc.setAccountPassword(rs.getString("AccountPassword"));
                acc.setAccountRole(rs.getString("AccountRole"));
                return acc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM tbl_Accounts";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setAccountUsername(rs.getString("AccountUsername"));
                acc.setAccountPassword(rs.getString("AccountPassword"));
                acc.setAccountRole(rs.getString("AccountRole"));
                accounts.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // ✅ Xóa tài khoản theo ID
    public boolean deleteAccountById(int accountId) {
        String sql = "DELETE FROM tbl_Accounts WHERE AccountId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Cập nhật thông tin tài khoản (username, password)
    public boolean updateAccount(Account acc) {
        String sql = "UPDATE tbl_Accounts SET AccountUsername=?, AccountPassword=? WHERE AccountId=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getAccountUsername());
            ps.setString(2, acc.getAccountPassword());
            ps.setInt(3, acc.getAccountId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Đổi Role giữa User ↔ Staff
    public boolean toggleAccountRole(int accountId) {
        String getSql = "SELECT AccountRole FROM tbl_Accounts WHERE AccountId=?";
        String updateSql = "UPDATE tbl_Accounts SET AccountRole=? WHERE AccountId=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement getPs = conn.prepareStatement(getSql)) {
            getPs.setInt(1, accountId);
            ResultSet rs = getPs.executeQuery();

            if (rs.next()) {
                String currentRole = rs.getString("AccountRole");
                String newRole = currentRole.equals("User") ? "Staff" : "User";

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setString(1, newRole);
                    updatePs.setInt(2, accountId);
                    return updatePs.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
