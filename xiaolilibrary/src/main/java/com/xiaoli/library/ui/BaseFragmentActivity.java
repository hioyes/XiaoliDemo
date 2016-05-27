package com.xiaoli.library.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xiaoli.library.C;
import com.xiaoli.library.utils.ThreadPoolUtils;

/**
 * @ClassName: BaseActivity
 * @author xiaokx Email:hioyes@qq.com
 * @date 2014-11-6 下午8:17:55
 * @Description:FragmentActivity基类
 */
public abstract class BaseFragmentActivity extends FragmentActivity{


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

    /**
     * activity跳转-最简单的跳转
     * @param context 当前上下文
     * @param clazz 目标类
     */
    public void forward(Context context,Class<?> clazz){
        this.forward(context, clazz, false, null, false, -1);
    }

    /**
     * activity跳转-选哟finish的跳转
     * @param context 当前上下文
     * @param clazz 目标类
     * @param isFinish 是否需要调用finish方法
     */
    public void forward(Context context,Class<?> clazz,boolean isFinish){
        this.forward(context, clazz, isFinish, null, false, -1);
    }

    /**
     * activity跳转-需要回调的跳转
     * @param context 当前上下文
     * @param clazz 目标类
     * @param isFinish 是否需要调用finish方法
     * @param requestCode 请求码，用于回调
     */
    public void forward(Context context,Class<?> clazz,boolean isFinish,int requestCode ){
        this.forward(context, clazz, isFinish, null, true, requestCode);
    }

    /**
     * activity跳转-带参数的跳转
     * @param context 当前上下文
     * @param clazz 目标类
     * @param isFinish 是否需要调用finish方法
     * @param params  参数
     */
    public void forward(Context context,Class<?> clazz,boolean isFinish,Bundle params){
        this.forward(context, clazz, isFinish, params, false, -1);
    }

    /**
     *  activity跳转-带参数、且需要回调的跳转
     * @param context 当前上下文
     * @param clazz 目标类
     * @param isFinish 是否需要调用finish方法
     * @param params  传递参数
     * @param isBack 是否需要回调onActivityResult
     * @param requestCode  请求码，用于回调
     */
    public void forward(Context context,Class<?> clazz,boolean isFinish,Bundle params,boolean isBack,int requestCode ){
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        if(params!=null)intent.putExtras(params);
        if(isBack)startActivityForResult(intent,requestCode);
        else startActivity(intent);
        if(isFinish)finish();
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
}
