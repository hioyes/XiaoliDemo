package com.xiaolidemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.xiaoli.library.C;
import com.xiaoli.library.utils.CrashHandler;
import com.xiaoli.library.utils.FileUtils;

import java.io.File;

/**
 * 应用全局对象
 * xiaokx
 * hioyes@qq.com
 * 2016-6-17
 */
public class BaseApp extends Application {

    // ===========常量==========
    private static final String TAG = "BaseApp";


    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext(), C.ROOT_CATALOG+"log/");
        C.init("com.xiaolidemo", Environment.getExternalStorageDirectory().getAbsolutePath()+"/xiaolidemo/",true);
//        C.openUMAnalytics(new MobclickAgent.UMAnalyticsConfig(this,"577c9911e0f55a7c78000149","arvin"));
        C.UMENG_ANALYTICS_ENABLE = true;
        C.NONE_CHEECK_VERSION.add("SplashActivity");
        C.NONE_CHEECK_VERSION.add("GuideActivity");
        if (Config.PRODUCTION_ENVIRONMENT == 2) {
            //准生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/quasi/version.js";
        } else if (Config.PRODUCTION_ENVIRONMENT == 3) {
            //生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/production/version.js";
        }
        C.DLG_UPDATE = R.layout.dlg_update;
        copyDbFile();
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Log.e(TAG,"umeng custom-->"+msg.custom);
                if(msg.extra!=null) {
                    Log.e(TAG, "umeng custom-->" +msg.extra.toString());
                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }




    /**
     * 数据库拷贝
     */
    private void copyDbFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                File dir = new File(Config.DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileUtils.copyDdFile("lbdetect.db", Config.DB_PATH, R.raw.lbdetect, getApplicationContext());
                FileUtils.copyDdFile("carmodel.db", Config.DB_PATH, R.raw.carmodel, getApplicationContext());
            }
        }).start();
    }
}
