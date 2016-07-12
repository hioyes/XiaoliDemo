package com.xiaolidemo.ui.demo;

import android.os.AsyncTask;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xiaoli.library.View.DropDownListView;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.CoreActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import butterknife.Bind;

/**
 * DropDownListView activity
 * xiaokx
 * hioyes@qq.com
 * 2016-7-12
 */
public class DropDownListViewActivity extends CoreActivity{

    private LinkedList<String> listItems = null;
    private ArrayAdapter<String> adapter;

    private String[]             mStrings            = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",};
    public static final int      MORE_DATA_MAX_COUNT = 3;
    public int moreDataCount = 0;

    @Bind(R.id.mDropDownListView)
    DropDownListView mDropDownListView;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_drop_down_listview;
    }


    @Override
    protected void initData() {
        super.initData();
        mDropDownListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                new GetDataTask(true).execute();
            }
        });

        mDropDownListView.setOnBottomListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new GetDataTask(false).execute();
            }
        });
        mDropDownListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PromptUtils.showToast("点击了->"+position);
            }
        });

        listItems = new LinkedList<String>();
        listItems.addAll(Arrays.asList(mStrings));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        mDropDownListView.setAdapter(adapter);
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        private boolean isDropDown;

        public GetDataTask(boolean isDropDown) {
            this.isDropDown = isDropDown;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                ;
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {

            if (isDropDown) {
                listItems.addFirst("Added after drop down");
                adapter.notifyDataSetChanged();

                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                mDropDownListView.onDropDownComplete(getString(R.string.update_at) + dateFormat.format(new Date()));
            } else {
                moreDataCount++;
                listItems.add("Added after on bottom");
                adapter.notifyDataSetChanged();

                if (moreDataCount >= MORE_DATA_MAX_COUNT) {
                    mDropDownListView.setHasMore(false);
                }

                mDropDownListView.onBottomComplete();
            }

            super.onPostExecute(result);
        }
    }
}
