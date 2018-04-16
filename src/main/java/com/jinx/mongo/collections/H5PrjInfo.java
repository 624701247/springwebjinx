package com.jinx.mongo.collections;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class H5PrjInfo {
    private String title;
    private String startTime;
    private String author;
    private String prjName;
    private String endTime;
    private String version;
    private boolean isRuning;

    public H5PrjInfo(String prjName) {
        this.prjName = prjName;
//        this.title = title;
    }

    public String getPrjName() {
        return this.prjName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVersion() {
        return this.version;
    }
}
