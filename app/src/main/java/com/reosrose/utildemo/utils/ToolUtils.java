package com.reosrose.utildemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 常用工具类
 * Created by yinsxi on 2017/11/8.
 */

public class ToolUtils {
    /**
     * 获取当前版本号，可以用于迭代数据库版本号
     * @param context
     * @return
     */
    public static int  getAppVersionCode(Context context){
        int localVersion = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            localVersion = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
