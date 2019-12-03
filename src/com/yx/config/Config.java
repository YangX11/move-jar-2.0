package com.yx.config;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Config {

    public static String pathProject = System.getProperty("user.dir");
    public static String resource = pathProject + File.separator +"hpmservice";;

    public static String pathSour = "C:\\application\\workspace\\hpm-tfs\\HPM\\hpmmicroservice";
    public static String pathDest = "";
    public static boolean rePackage = Boolean.TRUE;

    public static String message = defaultMeaasge();

    private static String[] disks = new String[]{"C:\\","D:\\","E:\\","F:\\"};

    static {
        //输出目录默认为用户桌面目录
        pathDest = FileSystemView.getFileSystemView() .getHomeDirectory().getAbsolutePath();
        //输入目录自动查找
        for (String disk : disks) {

        }
    }


    public static String currentTime(){
        return new SimpleDateFormat("[HH:mm:ss:SSS]").format(new Date());
    }

    public static String defaultMeaasge(){
        return currentTime() + "本程序仅适用于windows环境，需要java环境，若勾选`执行打包`则还需要maven环境..."
                + "\n" + currentTime() + "请确保环境变量配置的正确..."
                + "\n" + currentTime() + "上次执行时间[执行打包:999s,未执行打包:999s]...";
    }

}
