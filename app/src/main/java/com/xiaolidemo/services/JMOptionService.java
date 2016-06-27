package com.xiaolidemo.services;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xiaoli.library.C;
import com.xiaoli.library.utils.ThreadPoolUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 省市业务类
 * xiaokx
 * hioyes@qq.com
 * 2016-6-27
 */
public class JMOptionService extends CarModelService{

    public JMOptionService(Context context){
        super(context);
    }

    /**
     * 省市业务持久对象
     */
    private static JMOptionService mInstance;

    public static JMOptionService getInstance(Context context){
        if (mInstance == null) {
            synchronized (JMOptionService.class) {
                if (mInstance == null)
                    mInstance = new JMOptionService(context);
            }
        }
        return mInstance;
    }

    public void list(final Handler handler,final int taskid){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                List<Map> list = list();
//                List<Map> list2 = list2();
//                list.addAll(list2);
//                Message message = handler.obtainMessage();
//                message.obj = list;
//                message.what = taskid;
//                handler.sendMessage(message);
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.setName(UUID.randomUUID().toString());
//        thread.start();
//        ThreadPoolUtils.addMyThread(C.mCurrentActivity,thread);

        dbHelper.queryForList("select * from JM_Option ",Map.class,handler,taskid);
    }

    private List<Map> list(){
        Log.e("JMOptionService","lists="+System.currentTimeMillis());
        List<Map> list = dbHelper.queryForList("select * from JM_Option ",Map.class);
        Log.e("JMOptionService","liste="+System.currentTimeMillis());
        return list;
    }

    private List<Map> list2(){
        Log.e("JMOptionService","list2s="+System.currentTimeMillis());
        List<Map> list = dbHelper.queryForList("select * from JM_Option order by OptionCode ",Map.class);
        Log.e("JMOptionService","list2e="+System.currentTimeMillis());
        return list;
    }

}
