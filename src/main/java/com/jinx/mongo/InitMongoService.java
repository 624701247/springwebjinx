package com.jinx.mongo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class InitMongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void init() {
        System.out.println("mongoinit " + mongoTemplate);
        mongoTemplate.dropCollection("coll1");
    }
}
