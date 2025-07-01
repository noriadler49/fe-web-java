package com.mycompany.maven.mvc.project.resources;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=dtb_lastTermPrj;encrypt=true;trustServerCertificate=true";
        String user = "norial";
        String password = "Alice30th@111205";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, password);
    }
}
