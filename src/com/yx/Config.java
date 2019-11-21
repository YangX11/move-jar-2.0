package com.yx;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Config {

    static String pathProject = System.getProperty("user.dir");
    static String resource = pathProject + File.separator +"hpmservice";;

    static String pathSour = "C:\\application\\workspace\\hpm-tfs\\HPM\\hpmmicroservice";
    static String pathDest = "";
    static boolean rePackage = Boolean.TRUE;

    static String message = currentTime() + "本程序仅适用于windows环境，需要java环境，若勾选`执行打包`则还需要maven环境...";

    static {
        //输出目录默认为用户桌面目录
        pathDest = FileSystemView.getFileSystemView() .getHomeDirectory().getAbsolutePath();
    }

    static String currentTime(){
        return new SimpleDateFormat("[HH:mm:ss:SSS]").format(new Date());
    }

}
