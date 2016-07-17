package com.xiaolidemo.ui.demo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.xiaoli.library.C;
import com.xiaoli.library.service.FloatBallService;
import com.xiaoli.library.ui.NullActivity;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.CoreActivity;

import butterknife.Bind;

/**
 * xiaokx
 * hioyes@qq.com
 * 2016-7-15
 */
public class FloatBallActivity extends CoreActivity{

    @Bind(R.id.mBtnStart)
    Button mBtnStart;

    @Bind(R.id.mBtnCanel)
    Button mBtnCanel;
    @Override
    protected int getLayoutResId() {
        return R.layout.act_floatball;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatBallActivity.this, FloatBallService.class);
                startService(intent);
                C.exit(FloatBallActivity.this);
            }
        });
        mBtnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatBallActivity.this, FloatBallService.class);
                stopService(intent);
            }
        });
    }
}
