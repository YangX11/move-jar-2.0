package com.yx.utils;

import com.yx.Application;
import com.yx.config.Config;
import com.yx.frame.MainJFrame;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class LogUtils {

    private static Logger logger = Logger.getLogger(Application.class.getName());

    public static void printInfo(String message){
        logger.info(Config.currentTime() + message);
        StringBuffer sb = new StringBuffer(Config.message);
        Config.message = sb.append("\n" + Config.currentTime() + message).toString();
    }

    public static void printLogs(MainJFrame jFrame, String message){
        StringBuffer sb = new StringBuffer(Config.message);
        sb.append("\n" + Config.currentTime() + message);
        Config.message = sb.toString();
        jFrame.getTextArea1().setText(Config.message);
    }

    public static void logListener(MainJFrame jFrame){
        new Timer().schedule(new TimerTask() {
            int length = Config.message.length();
            @Override
            public void run() {
                if(Config.message.length() > length){
                    jFrame.getTextArea1().setText(Config.message);
                    length = Config.message.length();
                }
            }
        }, 0L, 100L);
    }
}
