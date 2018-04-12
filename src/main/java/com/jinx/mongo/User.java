package com.jinx.mongo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    private String name;
    private String age;
    User(String id) {
        this.name = id;
        this.age = "1111";
    }

}

