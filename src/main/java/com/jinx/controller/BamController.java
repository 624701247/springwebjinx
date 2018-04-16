//后台管理
package com.jinx.controller;

import com.jinx.mongo.PrjInfoMgr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin("http://test.com")

@RequestMapping("/bam")
public class BamController {

    public BamController() {
        System.out.println("bam init");
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String home(@RequestParam(value = "prjName", defaultValue = "")  String prjName) {
        System.out.println("bam home");
        return "bam/index";
    }

    //
    @RequestMapping(value="/prj_info_update", method = RequestMethod.GET)
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

    //
    @RequestMapping(value="/", method = RequestMethod.POST)
    @ResponseBody
    public String createPrj(@RequestParam(value = "a", defaultValue = "")  String action,
                            @RequestParam(value = "t", defaultValue = "")  String timestamp,
                            @RequestParam(value = "d", defaultValue = "{}")  String data) {
        switch (action) {
            case "prj_create":
                break;

            default:
                break;
        }
        return "" + action + " " + data;
    }


}
