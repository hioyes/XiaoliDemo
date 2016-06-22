package com.xiaolidemo.services;

import android.content.Context;

import com.xiaoli.library.db.DbHelper;
import com.xiaolidemo.Config;

/**
 * 离线库操作基类
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public  class CarModelService {

    private static String dbPath = Config.DB_PATH;
    private static String dbName = "carmodel.db";

    /**
     * DbHelper对象
     */
    protected static DbHelper dbHelper;



    public CarModelService(Context context){
        dbHelper = new DbHelper(context,dbPath,dbName);
    }
}
