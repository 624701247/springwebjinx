package com.jinx.mongo;

import com.jinx.mongo.collections.H5PrjInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DbService {

    private static DbService instance = new DbService();
    public static DbService inst(){
        return instance;
    }

    private HashMap<String, H5PrjInfo> prjInfoMap;


    private DbService() {
        this.prjInfoMap = new HashMap<String, H5PrjInfo>();

        H5PrjInfo pinfo = new H5PrjInfo("ktest", "哈哈哈");
        this.prjInfoMap.put("blue",  pinfo);
    }

    //
    public boolean isReady() {
        return true;
    }

    //
    public void changeInfo(String prjName, H5PrjInfo pinfo) {
        if(this.prjInfoMap.containsKey(prjName)) {
            this.prjInfoMap.remove(prjName);
        }
        this.prjInfoMap.put(prjName,  pinfo);
    }

    //
    public H5PrjInfo getPrjInfo(String prjName) {
        return this.prjInfoMap.get(prjName);
    }
}
