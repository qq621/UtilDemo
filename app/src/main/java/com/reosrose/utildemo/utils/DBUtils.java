package com.reosrose.utildemo.utils;

import android.content.Context;

import com.reosrose.utildemo.database.PersonDB;
import com.reosrose.utildemo.database.UserDB;
import com.reosrose.utildemo.vo.UserVo;

/**
 * Created by yinsxi on 2017/11/8.
 */

public class DBUtils {

    private static UserDB userDB;
    public static void init(Context context){
        userDB = new UserDB(context);
    }
    public static boolean getIsExitByName(Context context,String name){

        return userDB.getIsExitByName(name);
    }
    public static UserVo getUserByName(Context context,String name){

        return userDB.queryByName(name);
    }
    public static long registUser(Context context,UserVo userVo){
        return userDB.insert(userVo);
    }
}
