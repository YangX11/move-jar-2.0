package com.yx;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Timer;

/**
 * 主程序
 */
public class Application {

    static Map<String,String> moduleJarMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Application.start();
    }

    public static void start() throws Exception{
        String tempDir = "_" + new Date().getTime();
        StringBuffer sb = new StringBuffer("cmd /c ");
        if(Config.rePackage){
            sb.append(" cd " + Config.pathSour + " && mvn clean && mvn package &&");
        }
        //1、复制为临时文件
        sb.append(" xcopy " + Config.resource + " " + Config.pathProject + File.separator + tempDir + File.separator + " /e" );
        //2、根据项目路径，整理jar包
        forEachFile(new File(Config.pathSour));
        //3、复制moduleJar到临时文件
        moduleJarMap.forEach((fileName,filePath)->{     //迁移模块jar包
            sb.append(" && xcopy " + filePath + " " + Config.pathProject + File.separator + tempDir + File.separator + "module" + File.separator + getModule(fileName) + File.separator );
        });
        //4、复制/lib到临时文件
        sb.append(" && xcopy " + Config.pathSour + File.separator + "lib "  + Config.pathProject + File.separator + tempDir + File.separator + "lib" + File.separator + " /e");
        //5、将临时文件夹移动到外部目录
        sb.append(" && move " + Config.pathProject + File.separator + tempDir + " " + Config.pathDest + File.separator + tempDir);
        //run
        sb.append(" && echo every thing is done...");
        exectueCommand(sb.toString());
    }

    public static void  exectueCommand(String command){
        try {
            Process process = Runtime.getRuntime().exec(command);
            printCommandInfo(process.getInputStream()); //输出正常信息
            printCommandInfo(process.getErrorStream()); //输出异常信息
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public static void printLogs(MainJFrame jFrame,String message){
        StringBuffer sb = new StringBuffer(Config.message);
        sb.append("\n" + Config.currentTime() + message);
        Config.message = sb.toString();
        jFrame.getTextArea1().setText(Config.message);
    }

    public static void messageListener(MainJFrame jFrame){
        new Timer().schedule(new TimerTask() {
            int i = 0;
            int length = Config.message.length();
            String currentVal = jFrame.getTextArea1().getText();
            boolean isRunning = true;
            @Override
            public void run() {
                if(Config.message.length() > length){
                    isRunning = false;  //任意时刻message长度发生变化，则执行结束
                }
                if(isRunning){
                    jFrame.getTextArea1().setText(currentVal + "\n" + Config.currentTime() + "正在执行：" + i++);
                }else if(Config.message.length() > length){ //执行停止，且message长度变化时，则再次输出
                    jFrame.getTextArea1().setText(Config.message);
                    length = Config.message.length();
                }
            }
        }, 0L, 1000L);
    }
}
