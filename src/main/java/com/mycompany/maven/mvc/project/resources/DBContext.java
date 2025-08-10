package com.mycompany.maven.mvc.project.resources;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=MealDTB;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "12345";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, password);
    }
}
