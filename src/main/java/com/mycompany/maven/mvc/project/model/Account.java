package com.mycompany.maven.mvc.project.model;

public class Account {
    private int accountId;
    private String accountUsername;
    private String accountPassword;
    private String accountRole;

    // Constructors
    public Account() {}
    
    public Account(String username, String password, String role) {
        this.accountUsername = username;
        this.accountPassword = password;
        this.accountRole = role;
    }

    // Getters and setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int id) { this.accountId = id; }

    public String getAccountUsername() { return accountUsername; }
    public void setAccountUsername(String username) { this.accountUsername = username; }

    public String getAccountPassword() { return accountPassword; }
    public void setAccountPassword(String password) { this.accountPassword = password; }

    public String getAccountRole() { return accountRole; }
    public void setAccountRole(String role) { this.accountRole = role; }
}
