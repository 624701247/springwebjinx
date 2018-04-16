package com.jinx.mongo;

import com.jinx.mongo.collections.H5PrjInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PrjInfoMgr {

    private static PrjInfoMgr instance = new PrjInfoMgr();
    public static PrjInfoMgr inst(){
        return instance;
    }

    private HashMap<String, H5PrjInfo> prjInfoMap;


    private PrjInfoMgr() {
        this.prjInfoMap = new HashMap<String, H5PrjInfo>();

        H5PrjInfo pinfo = new H5PrjInfo("ktest");
        this.prjInfoMap.put("ktest",  pinfo);
    }

    //
    public void addItem(String prjName, H5PrjInfo pinfo) {
        if(this.prjInfoMap.containsKey(prjName)) {
            this.prjInfoMap.remove(prjName);
        }
        this.prjInfoMap.put(prjName,  pinfo);
    }

    //
    public H5PrjInfo getPrjInfo(String prjName) {
        return this.prjInfoMap.get(prjName);
    }

    //
    public boolean updateDb(String prjName) {
        if(InitMongoService.inst() == null) {
            System.out.println("数据库还没初始化");
            return false;
        }
        H5PrjInfo info = InitMongoService.inst().getH5PrjInfo(prjName);
        this.addItem(prjName, info);
        System.out.println("更新项目信息： " + prjName + "  " + info.getTitle());
        return true;
    }

    //
    public boolean addPrj() {
//        H5PrjInfo pinfo = new H5PrjInfo();
//        InitMongoService.inst().insertPrj(pinfo);
        return true; //
    }
}
