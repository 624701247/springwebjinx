package com.jinx.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static java.nio.file.Files.readAllLines;

@Controller
//@RestController  //  ?????

//@RequestMapping("/h5")   // http://localhost:8080/h5

public class MainController {
    private static String outDir = null;

    public MainController() {
        System.out.println("staticPath :" + this.getClass().getResource("/").getPath());
        System.out.println("rootPath: " +  System.getProperty("rootPath") );

        outDir = System.getProperty("rootPath");
    }

    @RequestMapping(value="/h5/{prjName}/game", method = RequestMethod.GET)
    public String blue(@PathVariable String prjName, @RequestParam(value = "token", defaultValue = "")  String token){
        System.out.println("prj name    : " + prjName);
        System.out.println("参数token值 : " + token );
        try {
            String str = this.getOutH5(prjName);
            return str;
        }
        catch (IOException err) {
            System.out.println("blue error" );
            return "";
        }
    }

    //
    private String getOutH5(String prjDir) throws IOException {
        String absDir = outDir + "h5/" + prjDir;
        String srcPath =  absDir + "/index.html";
        String destPath = absDir + "/index-out.html";
        String outStr = prjDir + "/index-out";

        File destfile = new File(destPath);
        if(destfile.exists()){
            System.out.println(destPath + " is exists!");
//            return outStr;   //kone todo ： 根据版本号确定是否需要重新生成静态h5
        }

        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(destfile),"utf-8"); //utf-8 解决中文乱码
        BufferedWriter writer = new BufferedWriter(write);
        Resource resource = new FileSystemResource(srcPath);

        File file = resource.getFile();
        List<String> list = readAllLines(file.toPath());

        String str = "";
        for (String string : list) {
            if(string.indexOf("<title>") != -1) {
                writer.write("<title>春天好啊</title>"); // kone todo : 后台设置标题
            }
            else if(string.indexOf("carry-local.js") != -1) {
                writer.write("<script src=\"carry.js\"></script>  ");
            }
            else {
                writer.write(string);
            }
        }
        writer.close();
        return outStr;
    }
}
