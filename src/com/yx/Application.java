package com.yx;

import com.yx.config.Config;
import com.yx.utils.CommandUtils;
import com.yx.utils.LogUtils;
import com.yx.utils.WriteUtil;

import java.io.*;
import java.util.*;

/**
 * 主程序
 */
public class Application {

    public static void main(String[] args) throws Exception {
        Application.start();
    }

    static Map<String,String> moduleJarMap = new HashMap<>();

    public static void start(){
        try{
            if(Config.rePackage){
                LogUtils.printInfo("正在执行命令[cmd /c cd /d " + Config.pathSour + " && mvn clean package]...");
                CommandUtils.exectueCommand("cmd /c cd /d " + Config.pathSour + " && mvn clean package");
            }
            String tempDir = "_" + new Date().getTime();
            //1、复制基础文件
            LogUtils.printInfo("正在准备基础文件...");
            WriteUtil.loadRecourseFromJarByFolder("/hpmservice",Config.pathDest + File.separator + tempDir);
            //2、根据项目路径，整理jar包
            LogUtils.printInfo("正在准备变更文件...");
            forEachFile(new File(Config.pathSour));
            //3、复制moduleJar到临时文件
            Iterator<Map.Entry<String,String>> it = moduleJarMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,String>  entry = it.next();
                LogUtils.printInfo("正在迁移模块文件[" + entry.getKey() + "]...");
                WriteUtil.cloneFile(entry.getValue(),Config.pathDest + File.separator + tempDir + File.separator  + "hpmservice" + File.separator + "module" + File.separator + getModule(entry.getKey()) + File.separator + entry.getKey());
            }
            //4、复制/lib到临时文件
            LogUtils.printInfo("正在迁移公共文件[/lib]...");
            WriteUtil.cloneDirAndFiles(Config.pathSour + File.separator + "lib",Config.pathDest + File.separator + tempDir + File.separator  + "hpmservice" + File.separator + "lib");
            LogUtils.printInfo("正在迁移公共配置文件[/config/resources]...");
            WriteUtil.cloneDirAndFiles(Config.pathSour + "\\flecy-api-service-config\\target\\resources",Config.pathDest + File.separator + tempDir + File.separator  + "hpmservice\\module\\config\\resources");
            //执行完成
            LogUtils.printInfo("everything is done...");
        }catch (Exception e){
            LogUtils.printInfo("执行出错：" + e.getMessage());
        }
    }

    public static String getModule(String fileName){
        String module = fileName.replace("flecy-api-","")
                .replace("-3.1.jar","")
                .replace("service","");
        if(module.indexOf("-") == 0){
            module = module.substring(1,module.length());
        }
        return module;
    }

    public static void forEachFile(File file) {
        if(file.isDirectory()) {
            Arrays.asList(file.listFiles()).forEach((dir)->forEachFile(dir));
        } else {
            if(isModuleJar(file.getName()) && !moduleJarMap.containsKey(file.getName())){
                moduleJarMap.put(file.getName(),file.getParent()+File.separator+file.getName());
            }
        }
    }

    public static boolean isCommonJar (String fileName) {
        return fileName.matches("flecy-api-common-3.1.jar");
    }

    public static boolean isModuleJar (String fileName) {
        return fileName.matches("flecy-api-.*-3.1.jar") && !isCommonJar(fileName);
    }

}
