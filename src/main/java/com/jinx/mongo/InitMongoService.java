package com.jinx.mongo;


import com.jinx.mongo.collections.H5PrjInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class InitMongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void init() {
        System.out.println("mongoTemplate1 " + mongoTemplate);

        this.listPrjInfos();
        instance = this;
    }

    private static InitMongoService instance;
    public static InitMongoService inst(){
        return instance;
    }

    //根据 prjName 查询 名为"h5-prj-infos" 的 collection 的一条 document
    public H5PrjInfo getH5PrjInfo(String prjName) {
        Query qy = new Query(Criteria.where("prjName").is(prjName));
        return this.mongoTemplate.findOne(qy, H5PrjInfo.class, "h5-prj-infos");
    }

    // 遍历
    public void listPrjInfos() {
        Query qy = new Query(Criteria.where("isRuning").is(true));
        List<H5PrjInfo> list = this.mongoTemplate.find(qy, H5PrjInfo.class, "h5-prj-infos");
        for(int idx = 0 ; idx < list.size() ; idx++) {
            H5PrjInfo info = list.get(idx);
            System.out.println(info);
            PrjInfoMgr.inst().addItem( info.getPrjName(), info );
        }

    }
}



// 删除数据库表 collection
//        mongoTemplate.dropCollection("players");

//插入一条记录到 collection
//        User us = new User("ktwo");
//        mongoTemplate.insert(us, "players");

//        H5PrjInfo info = this.getH5PrjInfo("blue");
//        System.out.println("sss " + info.getPrjName());