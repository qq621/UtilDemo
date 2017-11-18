package com.reosrose.utildemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.reosrose.utildemo.utils.ConstantUtli;
import com.reosrose.utildemo.utils.ToolUtils;

/**
 *操作数据库数据库
 * Created by yinsxi on 2017/11/7.
 */

public class DataBaseHepler extends SQLiteOpenHelper {

    public DataBaseHepler(Context context) {
        super(context, ConstantUtli.DB_NAME, null, ToolUtils.getAppVersionCode(context.getApplicationContext()));
//        super(context, ConstantUtli.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String PersonListSql = "create table Person ("+
                "integer primary key autoincrement"+
                ",name text not null"+
                ",age text"+
                ",sex text"+
                ",phoneNum text"+
                ",address text"+")";
        String UserSql ="create table User ("+
                "integer primary key autoincrement"+
                ",userName text not null"+
                ",password text not null)";
        db.execSQL(PersonListSql);
        db.execSQL(UserSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
