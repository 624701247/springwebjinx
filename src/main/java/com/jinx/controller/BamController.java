//后台管理
package com.jinx.controller;

import com.jinx.mongo.PrjInfoMgr;
//import org.json.JSONObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
//@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin("http://test.com")

@RequestMapping("/bam")
public class BamController {


    public BamController() {
        System.out.println("bam init");
    }

/*    kone point: 获取http 请求参数：
1、
@RequestParam(value = "prjName", defaultValue = "")  String prjName
若"Content-Type"="application/x-www-form-urlencoded"，post get都可以
若"Content-Type"="application/application/json"，只适用get

2、
@ModelAttribute("a") String action
获取POST请求的 from data 数据

end of *******************************/


    /*
    @RequestMapping(value="/bam", method = RequestMethod.GET)
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
    */

    //
    @RequestMapping(value="", method = RequestMethod.POST)
    @ResponseBody
    public String createPrj(
            @ModelAttribute("a") String action,
            @ModelAttribute("t") String timestamp,
            @ModelAttribute("d") String data
    ) {
        System.out.println("action: " + action + ";  timestamp: " + timestamp);
        System.out.println("data: " + data);

        JSONObject json = new JSONObject(data);
        JSONObject resp = new JSONObject();

        switch (action) {
            case "prj_create":
                break;

            case "prj_update":
                String prjName = json.getString("prjName");
                boolean flag = PrjInfoMgr.inst().updateDb( prjName );
                resp.put("f", flag);
                resp.put("prjName", prjName);
                break;

            default:
                break;
        }

        return resp.toString();
    }


}
