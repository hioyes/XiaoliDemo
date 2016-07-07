package com.xiaolidemo.ui;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xiaoli.library.ui.BaseFragmentActivity;

/**
 * 核心业务处理FragmentActivity
 * xiaokx
 * hioyes@qq.com
 * 2016-7-7
 */
public class CoreFragmentActivity extends BaseFragmentActivity{

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        PushAgent.getInstance(getApplicationContext()).onAppStart();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
