package com.xiaolidemo.ui;

import android.os.Message;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xiaoli.library.View.TitleBarLayout;
import com.xiaoli.library.ui.BaseActivity;
import com.xiaolidemo.R;

import butterknife.ButterKnife;

/**
 * 核心业务处理activity
 * xiaokx
 * hioyes@qq.com
 * 2016-7-6
 */
public class CoreActivity extends BaseActivity {

    /**
     * 页头
     */
    protected TitleBarLayout mTitleBarLayout;

    /**
     * 页头资源id
     * @return
     */
    protected int getTitleResId(){
        return R.id.mTitleBarLayout;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        mTitleBarLayout = (TitleBarLayout)findViewById(getTitleResId());

    }

    @Override
    protected void initListener() {
        if(mTitleBarLayout!=null){
            mTitleBarLayout.setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

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
