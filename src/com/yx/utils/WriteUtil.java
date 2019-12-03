package com.yx.utils;

import sun.net.www.protocol.file.FileURLConnection;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class WriteUtil {

    public static void loadRecourseFromJarByFolder(String folderPath, String targetFolderPath) throws Exception {
        URL url = WriteUtil.class.getResource(folderPath);
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof FileURLConnection) {
            copyFileResources(url, folderPath, targetFolderPath);
        } else if (urlConnection instanceof JarURLConnection) {
            copyJarResources((JarURLConnection) urlConnection, targetFolderPath);
        }
    }

    /**
     * 当前运行环境资源文件是在文件里面的
     */
    private static void copyFileResources(URL url, String folderPath, String targetFolderPath) throws Exception {
        File root = new File(url.getPath());
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    copyFileResources(WriteUtil.class.getResource(folderPath + "/" + file.getName()),folderPath + "/" + file.getName(),targetFolderPath);
                } else {
                    loadRecourseFromJar(folderPath + "/" + file.getName(), targetFolderPath);
                }
            }
        }else{
            loadRecourseFromJar(folderPath, targetFolderPath);
        }
    }

    private static void copyJarResources(JarURLConnection jarURLConnection, String targetFolderPath) throws Exception {
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            JarEntry entry = entrys.nextElement();
            if (entry.getName().startsWith(jarURLConnection.getEntryName()) && !entry.getName().endsWith("/")) {
                loadRecourseFromJar("/" + entry.getName(), targetFolderPath);
            }
        }
        jarFile.close();
    }

    public static void loadRecourseFromJar(String path, String targetFolderPath) throws Exception {
        String filename = path.substring(path.lastIndexOf('/') + 1);
        String folderPath = targetFolderPath + path.substring(0, path.lastIndexOf('/') + 1);
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        filename = folderPath + filename;
        File file = new File(filename);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        URL url = WriteUtil.class.getResource(path);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        OutputStream os = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = is.read(buffer)) != -1) {
            os.write(buffer, 0, readBytes);
        }
        os.close();
        is.close();
    }


    /********************************纯外部操作*******************************/
    /**
     * 复制一个文件夹及文件
     */
    public static void cloneDirAndFiles(String src,String dest) throws Exception{
        File fileSrc = new File(src);
        File[] fileSrcs = fileSrc.listFiles();
        File fileDest = new File(dest);
        if(!fileDest.exists()){
            fileDest.mkdirs();
        }
        for (File file : fileSrcs) {
            if(file.isFile()){
                cloneFile(file.getPath(),dest+"\\"+file.getName());
            }else if(file.isDirectory()){
                cloneDirAndFiles(file.getPath(),dest+"\\"+file.getName());  //递归
            }
        }
    }

    /**
     * 复制一个文件
     */
    public static void cloneFile(String src,String dest) throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
        int i = -1;
        byte[] bt = new byte[2014];
        while ((i = bis.read(bt))!=-1) {
            bos.write(bt, 0, i);
        }
        bis.close();
        bos.close();
    }

    private static String[] disks = new String[]{"C:\\","D:\\","E:\\","F:\\"};

    public static void selectPathSour(String path,String keyword) {
        File file = new File(path);
        if(file.isDirectory()){
            System.out.println(file.getAbsolutePath());
            File[] fileList = file.listFiles();
            if(fileList != null){
                for (File subFile : fileList) {
                    selectPathSour(subFile.getAbsolutePath(),keyword);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (String disk : disks) {
            selectPathSour(disk,"");
        }
    }

}
