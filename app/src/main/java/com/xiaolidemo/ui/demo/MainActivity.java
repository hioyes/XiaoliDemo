package com.xiaolidemo.ui.demo;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiaoli.library.camera.CameraActivity;
import com.xiaoli.library.utils.ForwardUtils;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.adapter.MainRecyclerViewAdapter;
import com.xiaolidemo.adapter.MainRecyclerViewDecoration;
import com.xiaolidemo.listener.OnRecyclerItemClickListener;
import com.xiaolidemo.ui.CoreActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Demo主页
 * 使用v7包中的RecyclerView组件展示所有代码工厂的所有功能模块
 * xiaokx
 * hioyes@qq.com
 * 2016-7-6
 */
public class MainActivity extends CoreActivity implements View.OnClickListener{

    /**
     * 列表控件
     */
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected int getTitleResId() {
        return R.id.mTitleBarLayout;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    protected void initData() {
        super.initData();
        String[] datas = {
                getResources().getString(R.string.puzzle_label),
                getResources().getString(R.string.demo_label),
                getResources().getString(R.string.DropDownListView_label),
                getResources().getString(R.string.slanted_label),
                getResources().getString(R.string.floatfall_label),
                getResources().getString(R.string.shape_image_label),
                getResources().getString(R.string.binner_label),
                getResources().getString(R.string.camera_label)
        };
        final List<Class> list = new ArrayList<>();
        list.add(PuzzleActivity.class);
        list.add(DemoActivity.class);
        list.add(DropDownListViewActivity.class);
        list.add(SlantedActivity.class);
        list.add(FloatBallActivity.class);
        list.add(ShapeImageActivity.class);
        list.add(BinnerActivity.class);
        list.add(CameraActivity.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MainRecyclerViewAdapter(datas));
        mRecyclerView.addItemDecoration(new MainRecyclerViewDecoration(this,MainRecyclerViewDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                ForwardUtils.to(MainActivity.this,list.get(position));
            }

            @Override
            public void onItemLOngClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                PromptUtils.showToast("长按了="+position);
                TextView mTvItem = (TextView) viewHolder.itemView.findViewById(R.id.mTvItem);
                mTvItem.setText("改变走这里开始");
            }
        });


//        List<Map<String, Object>> list = getData(path);
//        for (Map<String,Object> map: list) {
//            Log.e(TAG,map.get("title").toString());
//
//        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

        }
    }



}
