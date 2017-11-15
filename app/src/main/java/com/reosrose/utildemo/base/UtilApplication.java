package com.reosrose.utildemo.base;

import android.app.Application;

import com.reosrose.utildemo.utils.CrashHandler;
import com.reosrose.utildemo.utils.DBUtils;
import com.reosrose.utildemo.utils.ScreenSwitchUtil;

/**
 * Created by yinsxi on 2017/11/7.
 */

public class UtilApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        DBUtils dbUtils = new DBUtils(this);
        //初始化全局异常捕捉
        CrashHandler.getIntance().init(getApplicationContext());
        ScreenSwitchUtil.init(getApplicationContext());

    }
}
