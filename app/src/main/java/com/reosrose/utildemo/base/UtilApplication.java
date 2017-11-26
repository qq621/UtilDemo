package com.reosrose.utildemo.base;

import android.app.Application;
import android.content.Context;

import com.reosrose.utildemo.utils.CrashHandler;
import com.reosrose.utildemo.utils.DBUtils;
import com.reosrose.utildemo.utils.ScreenSwitchUtil;

/**
 * Created by yinsxi on 2017/11/7.
 */

public class UtilApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DBUtils.init(this);
        //初始化全局异常捕捉
        CrashHandler.getIntance().init(getApplicationContext());
        ScreenSwitchUtil.init(getApplicationContext());

    }
    public static Context getContext(){
        return context;
    }
}
