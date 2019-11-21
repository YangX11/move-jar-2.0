package com.yx.utils;

import com.yx.config.Config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CommandUtils {

    /**
     * 命令执行方法
     */
    public static void  exectueCommand(String command){
        try {
            Process process = Runtime.getRuntime().exec(command);
            printCommandInfo(process.getInputStream()); //输出正常信息
            printCommandInfo(process.getErrorStream()); //输出异常信息
            process.waitFor();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 异步输出cmd信息
     */
    public static void printCommandInfo(InputStream inputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append("\n" + Config.currentTime() + line);
                    }
                    Config.message += sb.toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
