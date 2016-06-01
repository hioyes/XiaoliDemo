package com.xiaoli.library.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoli.library.C;
import com.xiaoli.library.utils.ThreadPoolUtils;


/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName: BaseActivity
 * @date 2014-11-6 下午8:21:12
 * @Description:Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null)
            mView = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initListener();
        initData();
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        C.mCurrentActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        C.mCurrentActivity = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ThreadPoolUtils.destoryMyThread(getActivity());
        C.release(getActivity(), 0);
    }
}
