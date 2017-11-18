package com.reosrose.utildemo.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinsxi on 2017/11/18.
 */

public class ActivityCollector {
    public static List<Activity> list = new ArrayList<>();

    public static void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        list.remove(activity);
    }

    public static void finishAll() {
        for (Activity a :
                list) {
            if (!a.isFinishing()) {
                a.finish();
            }
        }
        list.clear();
    }
}
