package com.xiaolidemo.services;

import android.content.Context;

import com.xiaoli.library.db.DbHelper;
import com.xiaolidemo.Config;
import com.xiaolidemo.model.Test;

import java.util.List;

/**
 * 测试本地库读写
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class TestService {

    private static String dbPath = Config.DB_PATH;
    private static String dbName = "lbdetect.db";

    /**
     * DbHelper对象
     */
    private static DbHelper<Test> dbHelper;

    /**
     * 分公司持久对象
     */
    private static TestService mInstance;

    private TestService(Context context){
        dbHelper = new DbHelper(context,dbPath,dbName);
    }


    public static TestService getInstance(Context context){
        if (mInstance == null) {
            synchronized (TestService.class) {
                if (mInstance == null)
                    mInstance = new TestService(context);
            }
        }
        return mInstance;

    }

    /**
     * 插入一条记录
     * @param test
     */
    public void insert(Test test){
        dbHelper.insert("test",test);
    }

    /**
     * 获取一个列表
     * @return
     */
    public List<Test> list(){
        List<Test> list = dbHelper.queryForList("select * from test",Test.class);
        return list;
    }

}
