package com.xiaolidemo.ui;

import android.os.Message;
import android.widget.ArrayAdapter;

import com.xiaoli.library.View.PullToRefreshListView;
import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.DateUtils;
import com.xiaolidemo.R;

import java.util.ArrayList;

/**
 * demo
 *
 * @author xiaokx on 2016-5-24 18:10
 * @Email:hioyes@qq.com
 */
public class DemoActivity extends BaseActivity {

    private PullToRefreshListView mPullListView;
    private ArrayList<String> mArrayList = new ArrayList<String>();

    @Override
    protected int getLayoutResId() {
        return R.layout.demo;
    }

    @Override
    protected void initView() {
        mPullListView = (PullToRefreshListView) findViewById(R.id.mPullListView);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPullListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        mPullListView.setLastUpdated(DateUtils.toString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
        mPullListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            Message message = mHandler.obtainMessage();
                            message.what = 111111;
                            message.obj="模拟发送消息";
                            mHandler.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void handleMessageImpl(Message msg) {
        super.handleMessageImpl(msg);
        if(msg.what == 111111){
            mPullListView.onRefreshComplete();
            mPullListView.setLastUpdated(DateUtils.toString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
        }
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
