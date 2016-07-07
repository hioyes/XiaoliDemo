package com.xiaolidemo.ui;

import android.os.Message;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xiaoli.library.ui.BaseActivity;

import butterknife.ButterKnife;

/**
 * 核心业务处理activity
 * xiaokx
 * hioyes@qq.com
 * 2016-7-6
 */
public class CoreActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        PushAgent.getInstance(getApplicationContext()).onAppStart();

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
