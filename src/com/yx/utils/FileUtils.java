package com.yx.utils;

import java.io.*;

public class FileUtils {

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


    /**
     * 删除一个文件夹及文件
     */
    public static void deleteDirAndFiles(String dirPath){
        File file = new File(dirPath);
        if(file.isFile()){
            file.delete();
        }else{
            File[] files = file.listFiles();
            if(files != null && files.length > 0 ){
                for (File ff : files) {
                    deleteDirAndFiles(ff.getAbsolutePath());
                }
            }
            file.delete();
        }
    }

}
