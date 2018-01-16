package com.reosrose.utildemo.utils;

import android.os.Environment;
import java.io.File;

/**
 * 路径工具类
 * Created by yinsxi on 2017/11/8.
 */

public class PathUtil {
    /**
     * 获取日子保存路径
     * */
    public static String getLogPath(){
       return Environment.getExternalStorageDirectory().getPath()+File.separator
               +ConstantUtli.APP_NAME+File.separator+"logFile"
               +File.separator;
    }

    /**
     * 获取工程目录
     * @return
     */
    public static String getAppDocPath(){
        return Environment.getExternalStorageDirectory().getPath()+File.separator
                +ConstantUtli.APP_NAME
                +File.separator;
    }
}
