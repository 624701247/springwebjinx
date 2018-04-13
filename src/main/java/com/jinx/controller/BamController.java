//后台管理
package com.jinx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/bam")
public class BamController {

    @RequestMapping(value="/prj-info-update", method = RequestMethod.GET)
    @ResponseBody
    public String infoUpdate(@RequestParam(value = "prjName", defaultValue = "")  String prjName) {
        if(prjName == "") { //更新全部

        }
        else { //更新指定项目

        }

        System.out.println("更新项目信息： " + prjName);
        return "{flag:true}";
    }
}
