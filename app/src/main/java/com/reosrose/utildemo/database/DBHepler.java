package com.reosrose.utildemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yinsxi on 2017/11/8.
 */

public class DBHepler extends DataBaseHepler {

    protected final SQLiteDatabase writableDatabase;
    private DBHepler dbHepler;

    public DBHepler(Context context) {
        super(context);
        if (dbHepler == null) {
            dbHepler = new DBHepler(context);
        }
        writableDatabase = dbHepler.getWritableDatabase();
    }
    //关闭数据库
    public void closeDB(){
        if(dbHepler != null ){
            dbHepler.close();
            dbHepler = null;
        }
    }
}
