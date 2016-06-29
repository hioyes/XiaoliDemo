package com.xiaolidemo.services;

import android.content.Context;
import android.util.Log;

import com.xiaolidemo.model.Test;

import java.util.List;

/**
 * 测试本地库读写
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class TestService extends DetectService {

    public TestService(Context context){
        super(context);
    }

    /**
     * 分公司持久对象
     */
    private static TestService mInstance;


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
//        dbHelper.insert("carInfo",test);

       int id =  dbHelper.insert("carInfo",test);
        Log.e("idd",id+"---0000000000000000000000000");
    }

    public void update(Test test){
        test.setVin(test.getVin()+"_update");
        dbHelper.update("carInfo",test,"id="+test.getId());
    }

    /**
     * 获取一个列表
     * @return
     */
    public List<Test> list(){
        List<Test> list = dbHelper.queryForList("select * from carInfo",Test.class);
        return list;
    }

}
