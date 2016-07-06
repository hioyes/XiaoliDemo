package com.xiaolidemo.ui.common;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.DensityUtils;
import com.xiaoli.library.utils.ForwardUtils;
import com.xiaolidemo.R;

import java.util.ArrayList;

/**
 * 引导页
 * @author xiaokx on 2016-6-1 17:11
 * @Email:hioyes@qq.com
 */
public class GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";
    private int[] resImgIds;
    private ArrayList<ImageView> imageViews;
    private ViewPager mVpGuide;
    public Button mBtnStart;
    private LinearLayout mLlPoint;//圆点

    @Override
    protected int getLayoutResId() {
        return R.layout.act_guide;
    }

    @Override
    protected void initView() {
        mVpGuide = (ViewPager)findViewById(R.id.mVpGuide);
        mBtnStart = (Button)findViewById(R.id.mBtnStart);
        mLlPoint = (LinearLayout)findViewById(R.id.mLlPoint);
    }

    @Override
    protected void initListener() {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtils.to(GuideActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        resImgIds = new int[]{R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3,R.mipmap.guide4};
        imageViews = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < resImgIds.length; i++) {
            imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(resImgIds[i]);
            imageViews.add(imageView);
        }
        mVpGuide.setAdapter(new MyPagerAdapter());
        mVpGuide.setOnPageChangeListener(new MyOnPageChangertener());//vp的滑动监听
        initPoint(resImgIds.length,0);

    }

    /**
     * 创建圆点
     * @param size
     * @param index
     */
    private void initPoint(int size,int index){
        mLlPoint.removeAllViews();
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtils.dip2px(getApplicationContext(),10),DensityUtils.dip2px(getApplicationContext(),10));
            lp.setMargins(0,0,DensityUtils.dip2px(getApplicationContext(),3),0);
            imageView.setLayoutParams(lp);
            if(i==index){
                imageView.setBackgroundResource(R.drawable.shape_guide_round_red);
            }else{
                imageView.setBackgroundResource(R.drawable.shape_guide_round_gray);
            }
            mLlPoint.addView(imageView);
        }

    }


    class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = imageViews.get(position);
            container.addView(iv);
            return iv;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class MyOnPageChangertener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            //重置点
            initPoint(imageViews.size(),position);
            //判断vp滑动到最后一个页面后显示button
            int endPage = imageViews.size() - 1;
            if (endPage == position) {
                mBtnStart.setVisibility(View.VISIBLE);
            }else {
                mBtnStart.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
