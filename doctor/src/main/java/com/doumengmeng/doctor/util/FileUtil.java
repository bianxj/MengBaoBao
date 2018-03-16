package com.doumengmeng.doctor.util;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/16.
 */

public class FileUtil {

    private static FileUtil util;

    public static FileUtil getIntance(){
        if ( util == null ){
            util = new FileUtil();
        }
        return util;
    }

    public File createNewFile(String path){
        if ( TextUtils.isEmpty(path) ){
            return null;
        }
        deleteFile(path);
        File file = new File(path);
        createFolder(file.getParent());
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public void deleteFile(String path){
        if (TextUtils.isEmpty(path) ){
            return;
        }
        File file = new File(path);
        deleteFile(file);
    }

    public void deleteFile(File file){
        if ( file == null ){
            return;
        }
        file.delete();
    }

    public void createFolder(String folderPath){
        File folder = new File(folderPath);
        if ( !folder.isDirectory() ){
            folder.mkdirs();
        }
    }

}
