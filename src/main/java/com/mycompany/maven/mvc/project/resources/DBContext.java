package com.mycompany.maven.mvc.project.resources;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBContext {

    private static final String uri = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "dtb_lastTermPrj";

    private static MongoClient mongoClient = MongoClients.create(uri);

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
