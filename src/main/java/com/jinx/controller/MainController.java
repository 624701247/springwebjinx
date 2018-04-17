package com.jinx.controller;

import com.jinx.mongo.PrjInfoMgr;
import com.jinx.mongo.collections.H5PrjInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

@Controller
//@RestController  //  ?????

public class MainController {
    private static String outDir = null;

//    private DbService dbs;

    public MainController() {
        System.out.println("staticPath :" + this.getClass().getResource("/").getPath());
        System.out.println("rootPath: " +  System.getProperty("rootPath") );

        outDir = System.getProperty("rootPath");
    }

    //ktest
//    @RequestMapping(value="/", method = RequestMethod.GET)
//    @ResponseBody
//    public String kone() {
//        System.out.println("koneK");
//        return "ss ";
//    }


    //ktest
    @RequestMapping(value="/kone", method = RequestMethod.GET)
    @ResponseBody
    public String kone() {
        System.out.println("koneK");
        return "ss ";
    }


    //
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
    private String getOutH5(String prjNamw) throws IOException {
        H5PrjInfo pinfo = PrjInfoMgr.inst().getPrjInfo(prjNamw);
        String version = pinfo.getVersion();

        String absDir = outDir + "h5/" + prjNamw;
        String srcPath =  absDir + "/index.html";
        String destPath = absDir + "/index-out" + version + ".html";
        String outStr = prjNamw + "/index-out" + version;

        File destfile = new File(destPath);
        if(destfile.exists()){
            System.out.println(destPath + " is exists!");
            this.delOldH5File(absDir, version);
            return outStr;
        }

        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(destfile),"utf-8"); //utf-8 解决中文乱码
        BufferedWriter writer = new BufferedWriter(write);
        Resource resource = new FileSystemResource(srcPath);

        File file = resource.getFile();
        List<String> list = readAllLines(file.toPath());

        for (String string : list) {
            if(string.indexOf("<title>") != -1) {
                writer.write("<title>" + pinfo.getTitle() + "</title>");
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


    //删除旧的生成的h5文件
    private void delOldH5File(String absDir, String version) {
        File tdir = new File(absDir);
        if (tdir.exists() && tdir.isDirectory()) {
            File[] files = tdir.listFiles();
            for(int idx = 0; idx < files.length; idx++) {
                System.out.println(files[idx].getName());
                String name = files[idx].getName();
                if( name.indexOf("index-out") != -1) {
                    if(name.indexOf(version) == -1 ) {
                        System.out.println("删除旧的生成的h5文件" + name);
                        files[idx].delete();
                        break;
                    }
                }
            }
        }
    }
}
