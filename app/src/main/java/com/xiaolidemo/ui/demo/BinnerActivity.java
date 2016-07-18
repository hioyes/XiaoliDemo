package com.xiaolidemo.ui.demo;

import com.recker.flybanner.FlyBanner;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.CoreActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 自动轮播Binner
 * https://github.com/FlyRecker/FlyBanner
 * xiaokx
 * hioyes@qq.com
 * 2016-7-18
 */
public class BinnerActivity extends CoreActivity{


    @Bind(R.id.mFlyBanner)
    FlyBanner mFlyBanner;

    @Bind(R.id.mFlyBanner2)
    FlyBanner mFlyBanner2;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_binner;
    }

    @Override
    protected void initData() {
        super.initData();

        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.guide1);
        images.add(R.mipmap.guide2);
        images.add(R.mipmap.guide3);
        images.add(R.mipmap.guide4);
        mFlyBanner.setImages(images);
        mFlyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PromptUtils.showToast("点击了第"+position+"张图片");
            }
        });


        List<String> urls = new ArrayList<>();
        urls.add("http://inner.lubaocar.com/Portals/0/VehiclePhoto/40922/Standard/c37ffd118d75497d9f5aabdae9be48f1.JPG");
        urls.add("http://inner.lubaocar.com/Portals/0/VehiclePhoto/41276/Standard/deacd00b7aa5417aa0efe0b9a1ca852a.JPG");
        urls.add("http://inner.lubaocar.com/Portals/0/VehiclePhoto/41306/Standard/de171c1eb8754310a8b1c64f0f18f5b8.JPG");
        urls.add("http://inner.lubaocar.com/Portals/0/VehiclePhoto/41476/Standard/e6ce96c26e914a16a9cbf19ea4d1f8b2.JPG");
        urls.add("http://inner.lubaocar.com/Portals/0/VehiclePhoto/41308/Standard/0fe2827e2676435692162c9d23b1d607.JPG");
        mFlyBanner2.setImagesUrl(urls);
        mFlyBanner2.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PromptUtils.showToast("点击了第"+position+"张图片");
            }
        });


    }


}
