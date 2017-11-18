package com.reosrose.utildemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by yinsxi on 2017/11/18.
 */

public class ToastUtil {
    private static Toast mToast;
    public static void show(Context context,String str){
        if(TextUtils.isEmpty(str)){
            return;
        }
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;


        if(mToast == null){
            mToast = Toast.makeText(context.getApplicationContext(),str,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(str);
        }
        mToast.setGravity(Gravity.TOP, 0, height / 3);
        mToast.show();
    }
}
