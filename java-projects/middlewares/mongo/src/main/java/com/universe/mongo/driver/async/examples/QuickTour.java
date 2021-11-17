package com.universe.mongo.driver.async.examples;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;

import java.util.concurrent.CountDownLatch;

/**
 * @author agui93
 * @since 2020/7/15
 */
public class QuickTour {


    public static void main(String[] args) throws InterruptedException {
        final String databaseName = "quickTour";
        final String collectionName = "test";

        //connect to the local database server
        MongoClient mongoClient = MongoClients.create();

        //get handle to  database
        MongoDatabase database = mongoClient.getDatabase(databaseName);


        //get a handle to the "test" collection
        final MongoCollection<Document> collection = database.getCollection(collectionName);


        // drop all the data in it;  每次运行保证初始时集合为空
        final CountDownLatch dropLatch = new CountDownLatch(1);
        collection.drop((result, t) -> {
            System.out.format("\n\nDrop database=%s,collection=%s\n", databaseName, collectionName);
            dropLatch.countDown();
        });
        dropLatch.await();

        // make a document and insert it
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));

//        final CountDownLatch insertLatch = new CountDownLatch(1);
        collection.insertOne(doc, (result, t) -> {
            System.out.println("One doc Inserted!");
//            insertLatch.countDown();
        });
//        insertLatch.await();

        collection.find().first((result, t) -> {
            System.out.println("findFirst:=" + result.toJson());
        });


        System.out.println();
        System.out.println();
    }

}
