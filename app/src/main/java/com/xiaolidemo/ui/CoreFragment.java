package com.xiaolidemo.ui;

import com.umeng.analytics.MobclickAgent;
import com.xiaoli.library.ui.BaseFragment;

/**
 * 核心业务处理fragment
 * xiaokx
 * hioyes@qq.com
 * 2016-7-7
 */
public class CoreFragment extends BaseFragment{
    @Override
    protected int getLayoutResId() {
        return 0;
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getContext().toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getContext().toString());
    }
}
