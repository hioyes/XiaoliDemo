package com.xiaolidemo.ui;

import android.os.Message;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaolidemo.R;

import java.util.ArrayList;

/**
 * demo
 *
 * @author xiaokx on 2016-5-24 18:10
 * @Email:hioyes@qq.com
 */
public class DemoActivity extends BaseActivity {

    private ArrayList<String> mArrayList = new ArrayList<String>();

    @Override
    protected int getLayoutResId() {
        return R.layout.demo;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void handleMessageImpl(Message msg) {
        super.handleMessageImpl(msg);
    }

    private ArrayList<String> getData() {
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        return mArrayList;
    }

    private ArrayList<String> getData2() {
        mArrayList = new ArrayList<String>();
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        return mArrayList;
    }
}
