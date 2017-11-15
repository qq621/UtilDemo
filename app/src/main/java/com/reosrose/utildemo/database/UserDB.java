package com.reosrose.utildemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.reosrose.utildemo.utils.LogUtils;
import com.reosrose.utildemo.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册用户实体类
 * Created by yinsxi on 2017/11/8.
 */

public class UserDB extends DBHepler {
    public UserDB(Context context) {
        super(context);
    }

    /**
     * 批量注册
     * @param list
     * @return
     */
    public long insertList(List<UserVo> list){
        ContentValues content = null;
        long result = -1;
        writableDatabase.beginTransaction();
        for (UserVo userVo :list) {
            content = new ContentValues();
            content.put("userName",userVo.getUserName());
            content.put("password",userVo.getPassword());
            result = writableDatabase.insert("User",null,content);
            LogUtils.d("UserDB insert 结果 = "+String.valueOf(result));
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        return result;
    }

    /**
     * 单个注册
     * @param userVo
     * @return
     */
    public long insert(UserVo userVo){
        long result = -1;
        ContentValues content = new ContentValues();
        content.put("userName",userVo.getUserName());
        content.put("password",userVo.getPassword());
        result = writableDatabase.insert("User",null,content);
        return result;

    }
    public UserVo queryByName(String name){

        String sql = "select * from User where name = ?";
        List<UserVo> list = new ArrayList<>();
        Cursor cursor = writableDatabase.rawQuery(sql,new String[]{name});
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                UserVo userVo = getUserVo(cursor);
                list.add(userVo);
            }
        }
        if(cursor != null){
            cursor.close();
        }
        return list.get(0);

    }
    public boolean getIsExitByName(String name){
        String sql = "select * from User where name = ?";
        Cursor cursor = writableDatabase.rawQuery(sql,new String[]{name});
        boolean flag = cursor.moveToNext();
        if(cursor != null){
            cursor.close();
        }
        return flag;
    }
    private UserVo getUserVo(Cursor cursor) {
        UserVo userVo = new UserVo();
        userVo.setUserName(cursor.getColumnName(cursor.getColumnIndex("userName")));
        userVo.setPassword(cursor.getColumnName(cursor.getColumnIndex("password")));
        return userVo;
    }
}
