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

    public DBUtils(Context context){

    }
    public static boolean getIsExitByName(Context context,String name){
        UserDB userDB = new UserDB(context);
        return userDB.getIsExitByName(name);
    }
    public static UserVo getUserByName(Context context,String name){
        UserDB userDB = new UserDB(context);
        return userDB.queryByName(name);
    }
    public static long registUser(Context context,UserVo userVo){
        UserDB userDB = new UserDB(context);
        return userDB.insert(userVo);
    }
}
