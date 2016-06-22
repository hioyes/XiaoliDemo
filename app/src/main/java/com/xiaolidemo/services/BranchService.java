package com.xiaolidemo.services;

import android.content.Context;

import com.xiaoli.library.db.DbHelper;
import com.xiaolidemo.Config;
import com.xiaolidemo.model.Branch;
import com.xiaolidemo.model.Test;

import java.util.List;

/**
 * 分公司业务逻辑与db处理类
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class BranchService {

    private static String dbPath = Config.DB_PATH;
    private static String dbName = "CarModel.db";

    /**
     * DbHelper对象
     */
    private static DbHelper<Branch> dbHelper;

    /**
     * 分公司持久对象
     */
    private static BranchService mInstance;

    private BranchService(Context context){
        dbHelper = new DbHelper(context,dbPath,dbName);
    }



    public static BranchService getInstance(Context context){
        if (mInstance == null) {
            synchronized (BranchService.class) {
                if (mInstance == null)
                    mInstance = new BranchService(context);
            }
        }
        return mInstance;

    }

    /**
     * 获取所有分公司
     * @return
     */
    public List<Branch> list(){
        List<Branch> list = dbHelper.queryForList("select * from Branch",Branch.class);
        return list;
    }

}
