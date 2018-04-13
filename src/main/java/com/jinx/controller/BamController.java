//后台管理
package com.jinx.controller;

import com.jinx.mongo.PrjInfoMgr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping("/bam")
public class BamController {

    public BamController() {
        System.out.println("bam init");
    }


    @RequestMapping(value="/prj-info-update", method = RequestMethod.GET)
    @ResponseBody
    public String infoUpdate(@RequestParam(value = "prjName", defaultValue = "")  String prjName) {
        boolean flag = false;
        if(prjName == "") { //更新全部

        }
        else { //更新指定项目
            flag = PrjInfoMgr.inst().updateDb(prjName);
        }
        return "{flag:" + flag + "}";
    }
}
