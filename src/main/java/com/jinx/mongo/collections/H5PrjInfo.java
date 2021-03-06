package com.jinx.mongo.collections;

import org.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;

//enum PrjState {
//    dev,
//    running,
//    close
//}

class PrjState {
    public static final String dev = "1";
    public static final String running = "2";
    public static final String close = "3";
}

@Document
public class H5PrjInfo {


    private String prjName;

    /* base info */
    private String author = "";
    private String desc = "";
    private String state = PrjState.dev;

    /**/
    private String title = "";
    private String startTime = "";
    private String endTime = "";
    private String version = "";


    public H5PrjInfo(String prjName) {
        this.prjName = prjName;
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

    /**/
    public JSONObject getJsonBaseInfo() {
        JSONObject resp = new JSONObject();

        resp.put("author", this.author);
        resp.put("desc", this.desc);
        resp.put("state", this.state);

        return resp;
    }
}
