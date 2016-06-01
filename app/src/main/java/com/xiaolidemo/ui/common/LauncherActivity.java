package com.xiaolidemo.ui.common;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.ForwardUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.DemoActivity;

/**
 * 启动页面
 * @author xiaokx on 2016-5-27 16:36
 * @Email:hioyes@qq.com
 */
public class LauncherActivity extends BaseActivity {

    private ImageView mIvLauncher;

    @Override
    protected int getLayoutResId() {
        return R.layout.launcher;
    }

    @Override
    protected void initView() {
        mIvLauncher = (ImageView)findViewById(R.id.mIvLauncher);
    }

    @Override
    protected void initListener() {
        //添加动画效果
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转界面
                ForwardUtils.to(LauncherActivity.this,DemoActivity.class);
                finish();
                //转动淡出效果1
                overridePendingTransition(R.anim.scale_rotate_in,R.anim.alpha_out);
            }
        });
        mIvLauncher.setAnimation(animation);

    }

    @Override
    protected void initData() {

    }
}
