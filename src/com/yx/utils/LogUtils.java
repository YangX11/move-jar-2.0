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

    public static void logListener(MainJFrame jFrame){
        new Timer().schedule(new TimerTask() {
            int length = Config.message.length();
            @Override
            public void run() {
                if(Config.message.length() > length){
                    jFrame.getTextArea1().setText(Config.message);
                    jFrame.getTextArea1().setCaretPosition(jFrame.getTextArea1().getText().length());//光标移动到最后
                    length = Config.message.length();
                }
            }
        }, 0L, 100L);
    }
}
