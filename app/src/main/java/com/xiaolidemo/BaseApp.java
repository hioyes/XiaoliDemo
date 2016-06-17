package com.xiaolidemo;

import android.app.Application;
import android.os.Environment;

import com.xiaoli.library.C;
import com.xiaoli.library.utils.ThreadPoolUtils;

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
        C.init("com.xiaolidemo", Environment.getExternalStorageDirectory().getAbsolutePath()+"/xiaolidemo/",true);
        C.NONE_CHEECK_VERSION.add("SplashActivity");
        C.NONE_CHEECK_VERSION.add("GuideActivity");
        if (Config.PRODUCTION_ENVIRONMENT == 2) {
            //准生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/quasi/version.js";
        } else if (Config.PRODUCTION_ENVIRONMENT == 3) {
            //生产
            C.CHECK_VERSION_URL = "http://app.lubaocar.com/android/analytics/production/version.js";
        }
        C.DLG_UPDATE = R.layout.update;
    }
}
