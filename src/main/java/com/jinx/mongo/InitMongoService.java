package com.jinx.mongo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class InitMongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void init() {
        System.out.println("mongoTemplate1 " + mongoTemplate);

        // 删除数据库表 collection
//        mongoTemplate.dropCollection("players");

        //

        User us = new User("ktwo");
        mongoTemplate.insert(us, "players");

    }

}
