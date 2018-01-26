package com.doumengmengandroidbady.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * 作者:边贤君
 * 描述:
 *
 * 创建日期: 2017/12/2 11:19
 */
public class MLog {

    private final static String LOG_DIR_NAME = "logs";
    private final static String DEFAULT_TAG = "DEFAULT";
//    private final static long ONE_DAY = 24 * 60 * 60 * 1000;

    private SimpleDateFormat format;
    private SimpleDateFormat fileFormat;
    private Builder builder;
    private MLog(Builder builder){
        format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.CHINA);
        fileFormat = new SimpleDateFormat("yyyy-MM-DD", Locale.CHINA);
        this.builder = builder;
    }

    public void info(String string){
        String content = getLogPrintInfo(Log.INFO,string,3);
        printLog(Log.INFO,content);
        writeLogToFile(content);
    }
    public void info(Throwable throwable){
        String s = cnvThrowToStr(throwable);
        String content = getLogPrintInfo(Log.INFO,s,3);
        printLog(Log.INFO,content);
        writeLogToFile(content);
    }

    public void warning(String string){
        String content = getLogPrintInfo(Log.WARN,string,3);
        printLog(Log.WARN,content);
        writeLogToFile(content);
    }
    public void warning(Throwable throwable){
        String s = cnvThrowToStr(throwable);
        String content = getLogPrintInfo(Log.WARN,s,3);
        printLog(Log.WARN,content);
        writeLogToFile(content);
    }

    public void error(String string){
        String content = getLogPrintInfo(Log.ERROR,string,3);
        printLog(Log.ERROR,content);
        writeLogToFile(content);
    }
    public void error(Throwable throwable){
        String s = cnvThrowToStr(throwable);
        String content = getLogPrintInfo(Log.ERROR,s,3);
        printLog(Log.ERROR,content);
        writeLogToFile(content);
    }

    public void debug(String string){
        String content = getLogPrintInfo(Log.DEBUG,string,3);
        printLog(Log.DEBUG,content);
        writeLogToFile(content);
    }
    public void debug(Throwable throwable){
        String s = cnvThrowToStr(throwable);
        String content = getLogPrintInfo(Log.DEBUG,s,3);
        printLog(Log.DEBUG,content);
        writeLogToFile(content);
    }

    private void printLog(int type,String content){
        switch (type){
            case Log.INFO:
                Log.i(DEFAULT_TAG,content);
                break;
            case Log.WARN:
                Log.w(DEFAULT_TAG,content);
                break;
            case Log.ERROR:
                Log.e(DEFAULT_TAG,content);
                break;
            case Log.DEBUG:
                Log.d(DEFAULT_TAG,content);
                break;
        }
    }

    /**
     * 作者:边贤君
     * 描述:
     */
    private String getLogTypeName(int type){
        switch (type){
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARNING";
            case Log.ERROR:
                return "ERROR";
            case Log.DEBUG:
                return "DEBUG";
        }
        return "INFO";
    }

    private void writeLogToFile(String content){
        clearUnuseLog();
        if ( !builder.isSaveLog() ){
            return;
        }

        OutputStream outputStream = null;

        try {
            File file = getLogFile();
            outputStream = new FileOutputStream(file,true);
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( outputStream != null ){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearUnuseLog(){
        String dirPath = getLogDir();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        if ( files == null ){
            return;
        }

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (int i = builder.getSaveDay(); i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * 作者:边贤君
     * 描述:获取日志文件
     */
    private File getLogFile() throws IOException {
        String fileName = fileFormat.format(System.currentTimeMillis());
        File file = new File(getLogDir()+File.separator+fileName);
        if ( !file.exists() ){
            file.createNewFile();
        }
        return file;
    }

    /**
     * 作者:边贤君
     * 描述:获取日志文件保存目录
     */
    private String getLogDir(){
        String dir = null;
        if ( builder.isInner() ){
            dir = builder.getContext().getFilesDir().getPath()+File.separator+LOG_DIR_NAME;
        } else {
            dir = Environment.getExternalStorageDirectory().getPath()+File.separator+LOG_DIR_NAME;
        }
        dir = dir + builder.getLogDirName();
        File d = new File(dir);
        if ( !d.exists() || !d.isDirectory() ){
            d.mkdirs();
        }
        return dir;
    }

    /**
     * 作者:边贤君
     * 描述:获取日志打印时信息
     */
    private String getLogPrintInfo(int tag,String msg,int level){
        StringBuilder sb = new StringBuilder();
        sb.append(" [").append(getLogTypeName(tag)).append("]");
        sb.append(format.format(System.currentTimeMillis()));
        StackTraceElement element = Thread.currentThread().getStackTrace()[level];
        String className = element.getClassName();
        String methodName = element.getMethodName();
        int lineNum = element.getLineNumber();

        sb.append(" ").append(className).append(".").append(methodName);
        sb.append("(").append(lineNum).append(") \n");
        sb.append(msg).append("\n");
        return sb.toString();
    }

    /**
     * 作者:边贤君
     * 描述:将Throwable转换为String
     */
    private String cnvThrowToStr(Throwable throwable){
        if ( throwable == null ) {
            return "Throwable is Null";
        }

        ByteArrayOutputStream os = null;
        PrintStream stream = null;
        String content = null;
        try {
            os = new ByteArrayOutputStream();
            stream = new PrintStream(os);
            throwable.printStackTrace(stream);
            content = os.toString();
        } catch (Exception e) {
            content = "Throwable read error";
            e.printStackTrace();
        } finally {
            if ( stream != null ){
                stream.close();
            }
            if ( os != null ){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    public static class Builder{

        private Context context;
        private boolean isShow = true;
        private boolean isInner = true;
        private boolean isSaveLog = false;
        private boolean isDebug = false;
        private int saveDay = 5;
        private String logDirName;

        public Builder(Context context) {
            this.context = context;
        }

        public MLog build(){
            return new MLog(this);
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public void setInner(boolean inner) {
            isInner = inner;
        }

        public void setSaveLog(boolean saveLog) {
            isSaveLog = saveLog;
        }

        public void setSaveDay(int saveDay) {
            this.saveDay = saveDay;
        }

        public void setLogDirName(String logDirName) {
            this.logDirName = logDirName;
        }

        public void setDebug(boolean debug) {
            isDebug = debug;
        }

        public Context getContext() {
            return context;
        }

        public boolean isShow() {
            return isShow;
        }

        public boolean isInner() {
            return isInner;
        }

        public boolean isSaveLog() {
            return isSaveLog;
        }

        public boolean isDebug() {
            return isDebug;
        }

        public int getSaveDay() {
            return saveDay;
        }

        public String getLogDirName() {
            if ( logDirName == null ){
                return "";
            }
            String path = logDirName.trim();
            if ( !path.startsWith(File.separator) ){
                path = File.separator + path;
            }
            return path;
        }
    }

}
