package com.xiaolidemo.services;

import android.content.Context;
import android.util.Log;

import com.xiaolidemo.model.Branch;

import java.util.List;

/**
 * 分公司业务逻辑与db处理类
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class BranchService extends CarModelService{

    public BranchService(Context context) {
        super(context);
    }

    /**
     * 分公司持久对象
     */
    private static BranchService mInstance;

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
        add();
        return list;
    }

    public void add(){
        Branch branch = new Branch();
        branch.setBranchCode("33333sa");
        branch.setBranchID(334);
        branch.setBranchName("dddd");
        int id = dbHelper.insert("Branch",branch);
        Log.e("idd",id+"---Branch");
    }

}
