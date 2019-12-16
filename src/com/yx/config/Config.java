package com.yx.config;

import com.yx.utils.Constant;
import com.yx.utils.PropertyUtils;

import javax.swing.filechooser.FileSystemView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Config {

    public static String pathSour;
    public static String pathDest;
    public static boolean rePackage;
    public static String message;

    static {

        pathSour = PropertyUtils.selectProperties(Constant.PATH_INPUT);
        pathDest = PropertyUtils.selectProperties(Constant.PATH_OUTPUT);

        rePackage = Boolean.TRUE;

        //若没有设置输出目录，默认为用户桌面目录
        if("".equals(pathDest)){
            pathDest = FileSystemView.getFileSystemView() .getHomeDirectory().getAbsolutePath();
        }
        //设置默认提示信息
        String reTime = PropertyUtils.selectTimeAvg(Constant.PACKAGE_RE_TIME);
        String noTime = PropertyUtils.selectTimeAvg(Constant.PACKAGE_NO_TIME);
        message = currentTime() + "本程序仅适用于windows环境，需要java环境，若勾选`执行打包`则还需要maven环境..."
                + "\n" + currentTime() + "请确保环境变量配置的正确..."
                + "\n" + currentTime() + "平均执行时间[执行打包：(是：" + reTime + "s，否：" + noTime + "s)]...";
    }

    public static String currentTime(){
        return new SimpleDateFormat("[HH:mm:ss:SSS]").format(new Date());
    }

}
