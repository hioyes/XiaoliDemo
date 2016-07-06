package com.xiaolidemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.R;

/**
 * 主页列表(RecyclerView)适配器
 * xiaokx
 * hioyes@qq.com
 * 2016-7-6
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    public String[] datas = null;

    public MainRecyclerViewAdapter(String[] datas) {
        this.datas = datas;
    }

    /**
     * 创建新View，被LayoutManager所调用
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adp_main_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 将数据与界面进行绑定的操作
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTvItem.setText(datas[position]);
    }

    /**
     * 获取数据的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.length;
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView mTvItem;
        public Button mBtnDemo;

        public ViewHolder(View view) {
            super(view);
            mTvItem = (TextView) view.findViewById(R.id.mTvItem);
            mTvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptUtils.showToast("点击了ViewHolder中TextView");
                }
            });
            Button mBtnDemo = (Button) view.findViewById(R.id.mBtnDemo);
            mBtnDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PromptUtils.showToast("点击了ViewHolder中Button");
                }
            });
        }
    }
}
