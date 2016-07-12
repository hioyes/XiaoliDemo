package com.xiaolidemo.ui.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiaoli.library.utils.ForwardUtils;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.adapter.MainRecyclerViewAdapter;
import com.xiaolidemo.adapter.MainRecyclerViewDecoration;
import com.xiaolidemo.listener.OnRecyclerItemClickListener;
import com.xiaolidemo.ui.CoreActivity;

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
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    protected void initData() {
        super.initData();
        String[] datas = {
                getResources().getString(R.string.demo_label),
                getResources().getString(R.string.DropDownListView_label)
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MainRecyclerViewAdapter(datas));
        mRecyclerView.addItemDecoration(new MainRecyclerViewDecoration(this,MainRecyclerViewDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                if(position==0){
                    ForwardUtils.to(MainActivity.this,DemoActivity.class);
                }
                else if(position==1){
                    ForwardUtils.to(MainActivity.this,DropDownListViewActivity.class);

                }

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
