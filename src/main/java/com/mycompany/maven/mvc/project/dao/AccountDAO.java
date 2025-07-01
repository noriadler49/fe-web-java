package com.mycompany.maven.mvc.project.dao;

import com.mycompany.maven.mvc.project.model.Account;
import com.mycompany.maven.mvc.project.resources.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

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

}
