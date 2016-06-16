package com.xiaoli.library.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.xiaoli.library.C;
import com.xiaoli.library.net.CommonHandler;
import com.xiaoli.library.net.HttpWrapper;
import com.xiaoli.library.utils.ThreadPoolUtils;

/**
 * FragmentActivity基类
 *  xiaokx
 *  hioyes@qq.com
 *  2014-11-6
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements CommonHandler.HandlerWork,View.OnClickListener{

    protected HttpWrapper mHttpWrapper = new HttpWrapper().getInstance();
    protected Handler mHandler = CommonHandler.getInstance().getHandler();
    @Override
    public void handleMessageImpl(Message msg) {
    }

    /**
     * 页面标签
     */
    protected String TAG = getClass().getSimpleName();


    /**
     * 获取布局文件的资源id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        C.mCurrentActivity = this;
        setContentView(getLayoutResId());
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        C.mCurrentActivity = this;
    }

    public void replaceFragment(int containerViewId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId,fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        C.release(this,0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ThreadPoolUtils.destoryMyThread(this);
    }

    @Override
    public void onClick(View v) {

    }
}
