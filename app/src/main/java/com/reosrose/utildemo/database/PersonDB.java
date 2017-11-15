package com.reosrose.utildemo.database;

import android.content.ContentValues;
import android.content.Context;
import com.reosrose.utildemo.utils.LogUtils;
import com.reosrose.utildemo.vo.PersonVo;

import java.util.List;

/**
 * 人物数据操作
 * Created by yinsxi on 2017/11/8.
 */

public class PersonDB extends DBHepler {
    public PersonDB(Context context) {
        super(context);
    }
    public long insert(List<PersonVo> list){
        ContentValues content = null;
        long result = -1;
        writableDatabase.beginTransaction();
        for (PersonVo personVo :list) {
            content = new ContentValues();
            content.put("name",personVo.getName());
            content.put("age",personVo.getAge());
            content.put("sex",personVo.getSex());
            content.put("phoneNum",personVo.getPhoneNum());
            content.put("address",personVo.getAddress());
            result = writableDatabase.insert("Person",null,content);
            LogUtils.d("PersonDB insert 结果 = "+String.valueOf(result));
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        return result;
    }
}
