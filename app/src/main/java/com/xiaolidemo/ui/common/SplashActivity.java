package com.xiaolidemo.ui.common;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.ForwardUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.CoreActivity;

/**
 * 启动页面效果
 * @author xiaokx on 2016-6-1 15:58
 * @Email:hioyes@qq.com
 */
public class SplashActivity extends CoreActivity {

    private RelativeLayout mRlSplash;
    //动画集合
    private AnimationSet set;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {
        mRlSplash = (RelativeLayout)findViewById(R.id.mRlSplash);
        set = new AnimationSet(true);
        //旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

    }

    @Override
    protected void initListener() {
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ForwardUtils.to(SplashActivity.this, GuideActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData() {
        mRlSplash.startAnimation(set);
    }
}
