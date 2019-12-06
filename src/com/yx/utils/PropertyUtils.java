package com.yx.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Properties;

public class PropertyUtils {

    public static String selectProperties(String name) {
        Properties properties = new Properties();
        try {
            InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }

    public static void main(String[] args) {
        System.out.println(selectTimeAvg("time"));
//        writeTimeAvg("time","12356");
    }

    /****************************************外部文件**********************************************/

    private static String readFile() throws Exception{
        String path = System.getProperty("user.home") + File.separator + "movejar.properties";
        if(!new File(path).exists()){
            new File(path).createNewFile();
        }
        return path;
    }

    public static String selectTimeAvg(String key) {
        try {
            String filePath = readFile();
            Properties props = new Properties();
            InputStream in = new FileInputStream(filePath);
            props.load(in);
            if(props.getProperty(key) == null){
                return "-1";
            }
            return selectAvg(props.getProperty(key).split(","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static void writeTime(String key,String value) {
        try {
            String filePath = readFile();
            Properties props = new Properties();
            InputStream in = new FileInputStream(filePath);
            props.load(in);
            OutputStream fos = new FileOutputStream(filePath);
            props.setProperty(key, props.getProperty(key) == null ? value : props.getProperty(key) + "," + value);
            props.store(fos, "update{key:" + key + ",value:" + value + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String selectAvg(String[] strs){
        int sum = 0;
        for(String str : strs) {
            sum += Integer.valueOf(str);
        }
        return strs.length == 0 ? "-1" : String.valueOf(sum/strs.length);
    }
}