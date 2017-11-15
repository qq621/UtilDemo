package com.reosrose.utildemo.utils;

import android.content.Context;
import android.content.Intent;

import com.reosrose.utildemo.activity.MainActivity;

/**
 *
 * Created by yinsxi on 2017/11/8.
 */

public class GoActivityUtil {
    /**
     *跳转主界面
     * @param context
     */
    public static void goToMainActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }
}
