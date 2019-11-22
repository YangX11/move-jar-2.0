package com.yx;

import com.yx.config.Config;
import com.yx.utils.CommandUtils;
import com.yx.utils.FileUtils;
import com.yx.utils.LogUtils;

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
                LogUtils.printInfo("正在执行命令[mvn clean package]...");
                CommandUtils.exectueCommand("cmd /c cd " + Config.pathSour + " && mvn clean package");
            }
            String tempDir = "_" + new Date().getTime();
            //1、复制为临时文件
            LogUtils.printInfo("正在准备文件...");
            FileUtils.cloneDirAndFiles("resources" + File.separator + "hpmservice","resources" + File.separator + tempDir);
            //2、根据项目路径，整理jar包
            forEachFile(new File(Config.pathSour));
            //3、复制moduleJar到临时文件
            Iterator<Map.Entry<String,String>> it = moduleJarMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,String>  entry = it.next();
                LogUtils.printInfo("正在迁移模块文件[" + entry.getKey() + "]...");
                FileUtils.cloneFile(entry.getValue(),"resources" + File.separator + tempDir + File.separator + "module" + File.separator + getModule(entry.getKey()) + File.separator + entry.getKey());
            }
            //4、复制/lib到临时文件
            LogUtils.printInfo("正在迁移公共文件[/lib]...");
            FileUtils.cloneDirAndFiles(Config.pathSour + File.separator + "lib","resources" + File.separator + tempDir + File.separator + "lib");
            //5、写出到外部
            LogUtils.printInfo("正在写出到外部[" + Config.pathDest + File.separator + tempDir + "]...");
            FileUtils.cloneDirAndFiles("resources" + File.separator + tempDir,Config.pathDest + File.separator + tempDir);
            //6、删除临时文件夹
            LogUtils.printInfo("清理临时文件...");
            FileUtils.deleteDirAndFiles("resources" + File.separator + tempDir);
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
