package com.reosrose.utildemo.utils;

import android.util.Log;
import com.reosrose.utildemo.BuildConfig;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志工具类
 * Created by yinsxi on 2017/11/8.
 */

public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);//日期格式;
    private static String TAG = "LogUtils";


    private LogUtils(){
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
        writeToFile("e",className,createLog(message));
    }


    public static void i(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
        writeToFile("i",className,createLog(message));
    }

    public static void d(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
        writeToFile("d",className,createLog(message));
    }

    public static void w(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
        writeToFile("w",className,createLog(message));
    }


    //日志写入文件
    private static void writeToFile(String type, String tag, String msg){
        //log日志名，使用时间命名，保证不重复
        String fileName = PathUtil.getLogPath()+ dateFormat.format(new Date())+"_"+type+".log";
        //log日志内容，可以自行定制
//        String log = dateFormat.format(new Date()) + "_" + type + "_" + tag + "_" + msg + "\n";
        //如果父路径不存在
        File file = new File(PathUtil.getLogPath());
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }
        //FileOutputStream会自动调用底层的close()方法，不用关闭
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
