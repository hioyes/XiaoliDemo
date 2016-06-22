package com.xiaolidemo.services;

import android.content.Context;

import com.xiaoli.library.db.DbHelper;
import com.xiaolidemo.Config;
import com.xiaolidemo.model.Branch;

/**
 * 检查库基类
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class DetectService {

    private static String dbPath = Config.DB_PATH;
    private static String dbName = "lbdetect.db";

    /**
     * DbHelper对象
     */
    protected static DbHelper dbHelper;



    public DetectService(Context context){
        dbHelper = new DbHelper(context,dbPath,dbName);
    }
}
